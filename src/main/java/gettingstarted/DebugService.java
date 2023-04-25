package gettingstarted;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class DebugService {

    private static Log log = LogFactory.getLog(SpringContentApplication.class);

    public boolean debug(Object o) {
        log.info(String.format("****** DEBUG: {%s} ******", o.toString()));
//            AuthorizationManagerAfterMethodInterceptor
        return true;
    }
}
