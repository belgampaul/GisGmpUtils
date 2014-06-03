package com.bftcom.apps.gisgmp.ws.gisgmp.responses;

import ru.roskazna.xsd.errinfo.ErrInfo;

/**
 * Date: 14.01.13
 *
 * @author p.shapoval
 */
public class ErrorImportResponse  extends ImportResponse {
    public ErrorImportResponse() {
        super();
        ErrInfo errInfo = new ErrInfo();
        errInfo.setErrorCode("11");
        errInfo.setErrorDescription("Формат запроса не соответствует xsd-схеме.");
        errInfo.setErrorData("Инфа для админа");
        ticket.setRequestProcessResult(errInfo);
    }
}
