package com.sf.whht.container;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapProxy;
import com.sf.whht.spi.nscfg.WaybillService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

public class ContainerAction {
    private WaybillService waybillService;
    private MapProxy router;
    private Integer eventCode;
    private String waybill;
    private String container;

    private ContainerAction(WaybillService waybillService, Map<String, Object> router) {
        Assert.notNull(waybillService);
        Assert.notEmpty(router);
        this.waybillService = waybillService;
        this.router = MapProxy.create(router);
        this.eventCode = this.router.getInt("eventCode");
        this.waybill = this.router.getStr("sfWaybill");
        this.container = this.router.getStr("contnrCode");
    }

    public static ContainerAction use(WaybillService waybillService, Map<String, Object> router) {
        return new ContainerAction(waybillService, router);
    }

    private boolean filter() {
        List<Integer> operationCodeList = CollUtil.newArrayList(22, 23, 31, 32, 35);
        if (operationCodeList.contains(this.eventCode)) {
            switch (this.eventCode) {
                case 22:
                case 23:
                    return (waybillService.isWaybillNo(waybill, false) || waybillService.isPocketNo(waybill))
                            && waybillService.isContainerNo(container)
                            && !waybillService.isVehicleNo(waybill)
                            && !waybillService.isVehicleNo(container);
                case 31:
                case 32:
                    return waybillService.isWaybillNo(waybill, false)
                            && waybillService.isVehicleNo(container)
                            && !waybillService.isVehicleNo(waybill);
                case 35:
                    container = router.getStr("srcContnrCode");
                    return waybillService.isVehicleNo(waybill)
                            && waybillService.isVehicleNo(container);
                default:
                    break;
            }
        }
        return false;
    }

    public void callNo(BiConsumer<String, String> caller) {
        if (caller != null && filter()) {
            caller.accept(waybill, container);
        }
    }

    public ContainerAction callWaybill(BiConsumer<String, String> caller) {
        if (caller != null && filter() && !Objects.equals(eventCode, 35)
                && waybillService.isWaybillNo(waybill, true)) {
            caller.accept(waybill, container);
        }
        return this;
    }

    public ContainerAction callVehicle(BiConsumer<String, String> caller) {
        if (caller != null && filter() && Objects.equals(eventCode, 35)) {
            caller.accept(waybill, container);
        }
        return this;
    }
}
