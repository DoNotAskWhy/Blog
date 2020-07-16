package cn.xhxc.blog.controller.ex;

public class FileUploadEmpException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileUploadEmpException() {
    }

    public FileUploadEmpException(String message) {
        super(message);
    }

    public FileUploadEmpException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileUploadEmpException(Throwable cause) {
        super(cause);
    }

    public FileUploadEmpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
