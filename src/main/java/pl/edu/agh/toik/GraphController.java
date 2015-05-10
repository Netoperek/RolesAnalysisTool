package pl.edu.agh.toik;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by pkala on 5/10/15.
 */
@Controller
@RequestMapping("/showGraph")
public class GraphController {
    @RequestMapping(method = RequestMethod.GET)
    public String uploadGraphPage() {
        return "showGraph";
    }
}
