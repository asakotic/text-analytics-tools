package framework.test;

import framework.annotations.Controller;
import framework.annotations.Get;
import framework.annotations.Path;

@Controller
public class Test1 {

    @Get
    @Path(path = "/test")
    public String test1(){
        return "ee";
    }

}
