package framework.test;

import framework.annotations.Autowired;
import framework.annotations.Controller;
import framework.annotations.Get;
import framework.annotations.Path;

@Controller
public class Test3 {

    @Autowired
    private Singl jedan;
    @Autowired
    private Singl dva;


    @Path(path = "/sing")
    @Get
    public String test3(){
        return jedan.toString() + dva.toString();
    }
}
