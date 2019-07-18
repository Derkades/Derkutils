package xyz.derkades.derkutils;

/**
 * A variant of {@link AssertionError} that is a superclass of {@link RuntimeException} instead of {@link Error}, and is meant to be caught.
 * This exception is not made for boolean input, but to be thrown in instances where it being thrown is (or to the programmer seems) impossible.
 * <br>Example:
 * <pre>
 * boolean b = true;
 * String s;
 * if (b){
 *   s = "";
 * } else {
 *   throw new AssertionException();
 * }
 * System.out.println(s);
 * </pre>
 */
public class AssertionException extends RuntimeException {

	private static final long serialVersionUID = -1519059134126795928L;
	
	//public AssertionException(String message) {
	//	super(message);
	//}

}
