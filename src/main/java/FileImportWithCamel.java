import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class FileImportWithCamel {


    public static void main(String[] args) throws Exception {
    	CamelContext context = new DefaultCamelContext();
    	PropertyReader.loadProperties();
    	context.getPropertiesComponent().setLocation("classpath:resources.properties");
		context.addRoutes(new RouteBuilder() {
    		public void configure() {
                from("jetty:http://0.0.0.0:9080/myservice")
				.log("Trying to move file ${header:file_name}")
				.to("file:{{OUTPUT_DIRECTORY}}?fileName=${header:file_name}&doneFileName=${file:name}"+PropertyReader.ACK_OUTPUT)
    			.log("File ${header:file_name} is moved to {{OUTPUT_DIRECTORY}}");
    		}
    	});
    	context.start();
    	Thread.sleep(500000);
    	context.stop();
    }
}
