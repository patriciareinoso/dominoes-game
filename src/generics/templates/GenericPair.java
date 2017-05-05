package generics.templates;
import generics.abstractions.PairAbstraction;

public class GenericPair<T> extends PairAbstraction{

	protected T first;
	protected T second;
	
	public GenericPair(T fst, T snd){
		setFirst(fst);
		setSecond(snd);
	}
	
	public GenericPair(GenericPair<T> pair){
		first = pair.getFirst();
		second = pair.getSecond();
	}

	public T getFirst() {
		return first;
	}

	@Override
	public void setFirst(Object fst) {
		T newfirst = (T)fst;
		first = newfirst;
	}

	public T getSecond() {
		return second;
	}

	@Override
	public void setSecond(Object snd) {
		T newsecond = (T)snd;
		second = newsecond;
	}
	
	public static void swap(GenericPair pair){
		
		pair.swap();
	}
	
}
