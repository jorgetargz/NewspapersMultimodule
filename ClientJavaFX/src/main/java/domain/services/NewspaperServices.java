package domain.services;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import modelo.Newspaper;

import java.util.List;

public interface NewspaperServices {

    Single<Either<String, List<Newspaper>>> getNewspapers();

    Single<Either<String, Newspaper>> saveNewspaper(Newspaper newspaper);

    Single<Either<String, Newspaper>> updateNewspaper(Newspaper newspaper);

    Single<Either<String, Boolean>> deleteNewspaper(Newspaper newspaper);
}
