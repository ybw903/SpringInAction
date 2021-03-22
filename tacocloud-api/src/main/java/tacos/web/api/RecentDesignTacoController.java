package tacos.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.Taco;
import tacos.data.TacoRepository;

import java.util.List;
import java.util.Optional;

@RepositoryRestController
public class RecentDesignTacoController { //같은 이름의 빈 중복 불가, 어노테이션에 이름 추가 or BeanNameGenerator 직접 구현

    private TacoRepository tacoRepository;

    // https://docs.spring.io/spring-hateoas/docs/current/reference/html/#reference
    /*
        Spring HATEOAS
        @Autowired
        EntityLinks entityLinks;
     */

    public RecentDesignTacoController(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }


    @GetMapping(value = "/tacos/recent", produces = "application/hal+json")
    public CollectionModel<TacoResource> recentTacos() {
        // ResourceSupport is now RepresentationModel
        // Resource is now EntityModel
        // Resources is now CollectionModel
        // PagedResources is now PagedModel

        // Refactor TacoRepository extends PagingAndSortingRepository
        PageRequest page = PageRequest.of(0,12, Sort.by("createdAt").descending());
        List<Taco> tacos = tacoRepository.findAll(page).getContent();

        //List<TacoResource> tacoResources = new TacoResourceAssembler().toModel(tacos);
        CollectionModel<TacoResource> tacoResources = new TacoResourceAssembler().toCollectionModel(tacos);

        //CollectionModel<EntityModel<Taco>> recentResources = CollectionModel.wrap(tacos);
        tacoResources.add(
                WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(RecentDesignTacoController.class).recentTacos())
                .withRel("recents")
        );
        return tacoResources;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {

        Optional<Taco> optTaco = tacoRepository.findById(id);
        return optTaco.map(taco -> new ResponseEntity<>(taco, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco) {
        return tacoRepository.save(taco);
    }

}
