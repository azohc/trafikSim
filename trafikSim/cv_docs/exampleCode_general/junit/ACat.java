package tp.examples.junit;import static org.junit.Assert.*;import org.junit.Test;import org.junit.experimental.categories.Category;public class ACat {	@Test	public void a() {		fail();	}	@Category(SlowTests.class)	@Test	public void b() {	}}