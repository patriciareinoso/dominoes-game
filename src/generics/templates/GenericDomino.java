package generics.templates;

import generics.abstractions.PairAbstraction;
import generics.abstractions.PairSpecification;

public class GenericDomino<T> extends GenericPair<T> {
	
	public GenericDomino(T fst, T snd){
		super(fst,snd);
	}

	public boolean isDouble(){
		return (getFirst().equals(getSecond()));
	}

	@Override
	public boolean equals(Object obj){
		if (this == obj)
	           return true;
		 if (obj == null)
	           return false;
		 if (!(obj instanceof PairSpecification))
	           return false;

		 PairAbstraction other = (PairAbstraction) obj;

		 return ((other.getFirst().equals(getFirst()) && other.getSecond().equals(getSecond())) ||
				 (other.getFirst().equals(getSecond()) && other.getSecond().equals(getFirst())));
			
	 }
	
}
