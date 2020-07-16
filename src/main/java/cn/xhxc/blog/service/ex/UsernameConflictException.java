package cn.xhxc.blog.service.ex;

/**
 * 用户名冲突异常,例如:注册时用户名已经被占用
 */
public class UsernameConflictException extends ServiceException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsernameConflictException() {
    }

    public UsernameConflictException(String message) {
        super(message);
    }

    public UsernameConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameConflictException(Throwable cause) {
        super(cause);
    }

    public UsernameConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}