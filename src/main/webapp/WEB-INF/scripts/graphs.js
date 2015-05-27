/**
 * Created by pkala on 5/10/15.
 */

function drawGraph(graph, rolesKeys, rolesValues) {
    var simpleRoles = true;
    var rolesKeysArray = rolesKeys.split(", ");
    var rolesValuesArray = rolesValues.split(", ");
    var rolesMap = {};
    var i = 0;
    for(i = 0; i < rolesKeysArray.length; i++) {
        rolesMap[rolesKeysArray[i]] = rolesValuesArray[i];
    }
    console.log(rolesKeysArray);
    console.log(rolesValuesArray);
    console.log("Drawing graph " + graph);
    var width = 960,
        height = 500;

    var color = d3.scale.category20();

    var force = d3.layout.force()
        .charge(-120)
        .linkDistance(30)
        .size([width, height]);

    d3.select("svg").remove();

    var svg = d3.select(".graph")
        .append("svg")
        .attr("width", width)
        .attr("height", height);
    graphPath = "graphs/" + graph;
     d3.json(graphPath, function(error, graph) {
     force
     .nodes(graph.nodes)
     .links(graph.links)
     .start();

     var link = svg.selectAll(".link")
     .data(graph.links)
     .enter().append("line")
     .attr("class", "link")
     .style("stroke-width", function(d) { return Math.sqrt(d.value); });

     var node = svg.selectAll(".node")
     .data(graph.nodes)
     .enter().append("circle")
     .attr("class", "node")
     .attr("r", 5)
     .call(force.drag);

     node.append("title")
     .text(function(d) { return d.name; });

     var i = 0;
     if(simpleRoles) {
         for(var i = 0; i < graph.nodes.length; i++) {
             var key = graph.nodes[i].name;
             var value = rolesMap[key];
             if(value == 'MEDIATOR') {
                 graph.nodes[i].group = 0;
             } else if (value == 'STANDARD') {
                 graph.nodes[i].group = 1;
             } else {
                 graph.nodes[i].group = 2;
             }
         }

         document.getElementById("MEDIATOR").style.color = color(0);
         document.getElementById("MEDIATOR").style.visibility = 'visible';
         document.getElementById("STANDARD").style.color = color(1);
         document.getElementById("STANDARD").style.visibility = 'visible';
         document.getElementById("INFLUENTIAL").style.color = color(2);
         document.getElementById("INFLUENTIAL").style.visibility = 'visible';
     }

     node.style("fill", function(d) { return color(d.group); })

     force.on("tick", function() {
     link.attr("x1", function(d) { return d.source.x; })
     .attr("y1", function(d) { return d.source.y; })
     .attr("x2", function(d) { return d.target.x; })
     .attr("y2", function(d) { return d.target.y; });

     node.attr("cx", function(d) { return d.x; })
     .attr("cy", function(d) { return d.y; });
     });
     });
}

