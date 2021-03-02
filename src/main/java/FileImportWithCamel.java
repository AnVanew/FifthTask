import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class FileImportWithCamel {
    public static void main(String[] args) throws Exception {
    	CamelContext context = new DefaultCamelContext();
    	PropertyReader.loadProperties();
    	context.getPropertiesComponent().setLocation("classpath:resources.properties");
		String controlFile = "";
		if (!PropertyReader.ACK_INPUT.isEmpty()) {
			controlFile = "&doneFileName=${file:name}" + PropertyReader.ACK_INPUT;
		}
		String finalControlFile = controlFile;
		context.addRoutes(new RouteBuilder() {
    		public void configure() {
    			from("file:{{INPUT_DIRECTORY}}"+"?delay={{INITIAL_DELAY}}}&delete=true" + finalControlFile)
				.to("file:{{OUTPUT_DIRECTORY}}?");
    		}
    	});
    context.start();
    Thread.sleep(500000);
    context.stop();
    }
}
