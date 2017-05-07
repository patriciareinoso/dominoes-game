package generics.templates;

import generics.abstractions.PairAbstraction;

/**
 * Implements {@link generics.abstractions.PairAbstraction} class.
 * Represent a pair of generic objects.<br>
 * 
 * @author Patricia REINOSO
 * @version 1.0.0
 * @since 2017-04-26
 * */
public class GenericPair<T> extends PairAbstraction{

	/**
	 * Represent the first value of the pair.
	 */
	protected T first;
	
	/**
	 * Represent the second value of the pair.
	 */
	protected T second;
	
	/**
	 * Construct a Generic Pair of objects from a given first and second value.<br>
	 * 
	 * @param fst for the first value
	 * @param snd for the second value
	 * @throws IllegalArgumentException if fst or snd are null.
	 */
	public GenericPair(T fst, T snd) throws IllegalArgumentException{
		if (fst == null || snd == null){
			throw new IllegalArgumentException("Illegal arguments");
		}
		setFirst(fst);
		setSecond(snd);
	}
	
	/**
	 * A copy constructor - makes a new pair object which is  a copy of the given pair.<br>
	 *
	 * @param pair is the pair whose values are to be copied in the new object.
	 * @throws IllegalArgumentException if the domino to copy is null.
	 */
	public GenericPair(GenericPair<T> pair) throws IllegalArgumentException{
		if (pair == null){
			throw new IllegalArgumentException("Illegal arguments");
		}
		setFirst(pair.getFirst());
		setSecond(pair.getSecond());
	}

	
	/**
	 * Getter function of the first value.<br>
	 * @return the first value.
	 */
	public T getFirst() {
		return first;
	}

	/**
	 * Setter function of the first value.<br>
	 * @param fst object to set on the first value.
	 */
	@Override
	public void setFirst(Object fst) {
		T newfirst = (T)fst;
		first = newfirst;
	}

	/**
	 * Getter function of the first value.<br>
	 * @return the first value.
	 */
	public T getSecond() {
		return second;
	}

	/**
	 * Setter function of the first value.<br>
	 * @param snd object to set on the first value.
	 */
	@Override
	public void setSecond(Object snd) {
		T newsecond = (T)snd;
		second = newsecond;
	}
	
	/**
	 * Swap the values of a given pair.<br>
	 * 
	 * @param pair the pair to be swapped. 
	 * */
	public static void swap(GenericPair pair){
		pair.swap();
	}
	
}
