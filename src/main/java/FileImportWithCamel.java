import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class FileImportWithCamel {


    public static void main(String[] args) throws Exception {
    	CamelContext context = new DefaultCamelContext();
    	PropertyReader.loadProperties();
    	context.getPropertiesComponent().setLocation("classpath:resources.properties");
		context.addRoutes(new RouteBuilder() {
    		public void configure() {
    			from("file:{{INPUT_DIRECTORY}}?delay={{INITIAL_DELAY}}}&delete=true" + getDoneFile("&", ""))
				.log("Trying to move file ${file:name} from {{INPUT_DIRECTORY}}")
				.to("file:{{OUTPUT_DIRECTORY}}?" +getDoneFile("",""))
    			.log("File ${file:name} is moved to {{OUTPUT_DIRECTORY}}");
    		}
    	});
    	context.start();
    	Thread.sleep(500000);
    	context.stop();
    }

    private static String getDoneFile (String pre, String post){
		String controlFile = "";
		if (!PropertyReader.ACK_INPUT.isEmpty()) {
			controlFile = pre + "doneFileName=${file:name}" + PropertyReader.ACK_INPUT + post;
		}
		return controlFile;
	}
}
