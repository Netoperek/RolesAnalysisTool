package pl.edu.agh.toik;

/**
 * Created by pkala on 5/9/15.
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashSet;

@Controller
@RequestMapping("/graphs")
public class GraphController implements ServletContextAware {

    private ServletContext servletContext;
    private final String GRAPHS_DIR = "WEB-INF/graphs/";
    private HashSet<String> graphsFiles = new HashSet<String>();

    private void updateGrpahsFilesSet() {
        File[] files = new File(getGraphsDirectory()).listFiles();
        graphsFiles.clear();
        for (File file : files) {
            if (file.isFile()) {
                graphsFiles.add(file.getName());
            }
        }
    }

    private String getGraphsDirectory() {
        return servletContext.getRealPath("/") + File.separator + GRAPHS_DIR;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String graphPage(ModelMap model) {
        updateGrpahsFilesSet();
        model.addAttribute("uploaded", false);
        model.addAttribute("graphsFiles", graphsFiles);
        return "graphs";
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public String handleFileUpload(ModelMap model, @RequestParam("file") MultipartFile file) {
        String name = file.getOriginalFilename();
        model.addAttribute("graphsFiles", graphsFiles);
        model.addAttribute("uploaded", false);
        if (!file.isEmpty()) {
            model.addAttribute("uploaded", true);
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                File dir = new File(getGraphsDirectory());

                // Create the file on server
                File serverFile = new File(dir.getPath() + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile, false));
                stream.write(bytes);
                stream.close();

                System.out.println("Server File Location=" + serverFile.getAbsolutePath());
            } catch (Exception e) {

            }
        } else {

        }
        updateGrpahsFilesSet();
        return "graphs";
    }
}