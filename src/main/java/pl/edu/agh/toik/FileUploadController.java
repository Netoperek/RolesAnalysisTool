package pl.edu.agh.toik;

/**
 * Created by pkala on 5/9/15.
 */

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Controller
@RequestMapping("/uploadGraph")
public class FileUploadController implements ServletContextAware {

    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
    @RequestMapping(method = RequestMethod.GET)
    public String uploadGraphPage() {
        return "uploadGraph";
    }

    @RequestMapping(value="/upload", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "showGraph";
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file) {
        String name = file.getOriginalFilename();
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String projectPath = servletContext.getRealPath("/");
                File dir = new File(projectPath + File.separator + "WEB-INF/graphs/");
                System.out.println(dir.getPath());

                // Create the file on server
                File serverFile = new File(dir.getPath() + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                System.out.println("Server File Location=" + serverFile.getAbsolutePath());

                return new ModelAndView("redirect:/showGraph");
            } catch (Exception e) {
                return new ModelAndView("Exception");
            }
        } else {
            return new ModelAndView("File is empty");
        }
    }

}