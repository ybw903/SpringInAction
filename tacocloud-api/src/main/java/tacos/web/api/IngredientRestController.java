package tacos.web.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path ="/ingredientstsx", produces = "application/json")
@CrossOrigin(origins = "*")
public class IngredientRestController {

}
