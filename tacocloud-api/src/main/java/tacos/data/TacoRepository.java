package tacos.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import tacos.Taco;

//public interface TacoRepository extends CrudRepository<Taco, Long> {
//    //Taco save(Taco design);
//}

public interface TacoRepository extends PagingAndSortingRepository<Taco, Long> {

}