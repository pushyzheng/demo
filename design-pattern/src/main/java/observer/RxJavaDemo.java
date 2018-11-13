package observer;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * @author Pushy
 * @since 2018/11/13 16:22
 */
public class RxJavaDemo {

    public static void main(String[] args) {

        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onError(new Throwable("Error emitter"));
            }
        });

        /*observable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println(integer);
            }
        });*/

        observable.subscribe(integer -> {
            System.out.println("succeed => " + integer);
        }, throwable -> {
            System.out.println("error => " + throwable.getMessage());
        });



    }

}
