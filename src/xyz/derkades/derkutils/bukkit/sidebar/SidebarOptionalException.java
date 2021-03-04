package xyz.derkades.derkutils.bukkit.sidebar;

public class SidebarOptionalException extends RuntimeException {

	private static final long serialVersionUID = 0L;

	public SidebarOptionalException(final String message) {
		super(message);
	}

	public SidebarOptionalException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

}
