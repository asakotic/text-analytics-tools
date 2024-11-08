package framework.test;

import framework.annotations.*;

@Controller
public class Test2 {
    @Autowired(verbose = true)
    private Klasa klasa;
    @Autowired(verbose = true)
    @Qualifier("aaa")
    private Abs abs;

    @Get
    @Path(path = "/auto")
    public String test1(){
        return String.valueOf(abs);
    }

}
