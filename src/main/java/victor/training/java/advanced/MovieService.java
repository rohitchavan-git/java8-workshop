package victor.training.java.advanced;

import org.springframework.stereotype.Service;

@Service
public class MovieService {

    public Integer calculatePriceByType(MovieType type,int days){
        if (type==null) {
            throw new MySpecialCaseNotFoundException();
        }
        return type.moviePriceCalAlgo.apply(this, days);
    }

    public Integer priceForRegular(Integer days) {
        return days + 1;
    }

    public Integer priceForNewRelease(Integer days) {
        return days * 2;
    }

    public Integer priceForChildren(Integer integer) {
        return 5;
    }
}
