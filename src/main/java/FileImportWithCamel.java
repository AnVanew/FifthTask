import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class FileImportWithCamel {
    public static void main(String[] args) throws Exception {
		PropertyReader.loadProperties();
    	CamelContext context = new DefaultCamelContext();
    	context.addRoutes(new RouteBuilder() {
    		public void configure() {
    			from("file:"+PropertyReader.INPUT_DIRECTORY.toAbsolutePath().toString()+"?delete=true")
                .filter().method(Filter.class, "filter")
				.to("file:"+PropertyReader.OUTPUT_DIRECTORY.toAbsolutePath().toString());
    		}
    	});
    context.start();
    Thread.sleep(1000);
    context.stop();
    }
}
