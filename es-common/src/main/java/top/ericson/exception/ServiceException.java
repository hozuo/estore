package top.ericson.exception;

public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 6080887096742828769L;

    /**状态识别,是否打印栈跟踪 0:不打印 1:打印*/
    private Integer state;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Integer state) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
