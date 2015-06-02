package pl.edu.agh.toik;

/**
 * Created by pkala on 5/9/15.
 */

import edu.uci.ics.jung.graph.Graph;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/graphs")
public class GraphController implements ServletContextAware {

    private ServletContext servletContext;
    private final String GRAPHS_DIR = "WEB-INF/graphs/";
    private HashSet<String> graphsFiles = new HashSet<String>();
    private HashMap<String, HashMap<String, Role>> graphsRoles = new HashMap<String, HashMap<String, Role>>();
    private HashMap<String, Graph<String, MyLink>> graphs = new HashMap<String, Graph<String, MyLink>>();
    private HashMap<String, List<MyLink>> graphsEdges = new HashMap<String, List<MyLink>>();
    private GraphType typeDisplayed = GraphType.WITH_ROLES;
    private static int mediatorsPer = 30;
    private static int influentialPer = 40;

    private void updateGrpahsFilesSet() {
        File[] files = new File(getGraphsDirectory()).listFiles();
        graphsFiles.clear();
        if (files == null) return;
        for (File file : files) {
            if (file.isFile()) {
                graphsFiles.add(file.getName());
                Graph<String, MyLink> graph = GraphUtils.graphFromJson(file.getAbsolutePath());
                if(!graphsRoles.containsKey(file.getName())) {
                    HashMap<String, Role> roles = GraphUtils.markRoles(graph, mediatorsPer, influentialPer);
                    graphsRoles.put(file.getName(), roles);
                }
                if(!graphs.containsKey(file.getName())) {
                    graphs.put(file.getName(), graph);

                }
                List<MyLink> edges = new ArrayList<MyLink>(graph.getEdges());
                graphsEdges.put(file.getName(), edges);
            }
        }
    }

    private String getGraphsDirectory() {
        return servletContext.getRealPath("/") + GRAPHS_DIR;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String graphPage(ModelMap model) {
        updateGrpahsFilesSet();
        model.addAttribute("graphsEdges", graphsEdges);
        model.addAttribute("uploaded", false);
        model.addAttribute("typeDisplayed", typeDisplayed);
        model.addAttribute("graphsFiles", graphsFiles);
        model.addAttribute("graphsRoles", graphsRoles);
        model.addAttribute("mediatorsPer", mediatorsPer);
        model.addAttribute("influentialPer", influentialPer);
        return "graphs";
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public String handleFileUpload(ModelMap model, @RequestParam("file") MultipartFile file) {
        String name = file.getOriginalFilename();
        model.addAttribute("graphsEdges", graphsEdges);
        model.addAttribute("typeDisplayed", typeDisplayed);
        model.addAttribute("graphsFiles", graphsFiles);
        model.addAttribute("graphsRoles", graphsRoles);
        model.addAttribute("uploaded", false);
        model.addAttribute("mediatorsPer", mediatorsPer);
        model.addAttribute("influentialPer", influentialPer);
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
        }
        updateGrpahsFilesSet();
        return "graphs";
    }

    @RequestMapping(value="/updateRoles", method=RequestMethod.POST)
    public String handleRolesInput(ModelMap model, @RequestParam("mediator") Integer mediatorP,@RequestParam("influential") Integer influentialP) {
        this.influentialPer = influentialP;
        this.mediatorsPer = mediatorP;
        updateGrpahsFilesSet();
        model.addAttribute("graphsEdges", graphsEdges);
        model.addAttribute("typeDisplayed", typeDisplayed);
        model.addAttribute("graphsFiles", graphsFiles);
        model.addAttribute("graphsRoles", graphsRoles);
        model.addAttribute("uploaded", false);
        model.addAttribute("mediatorsPer", mediatorsPer);
        model.addAttribute("influentialPer", influentialPer);
        return "graphs";
    }
}