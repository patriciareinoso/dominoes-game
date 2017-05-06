package generics.templates;

import softwareGame.DominoInt;
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
		 //if (!(obj instanceof GenericDomino))
		 if (!this.getClass().equals(obj.getClass()))
	           return false;

		 GenericDomino other = (GenericDomino) obj;

		 return ((other.getFirst().equals(getFirst()) && other.getSecond().equals(getSecond())) ||
				 (other.getFirst().equals(getSecond()) && other.getSecond().equals(getFirst())));
			
	 }
	
	public boolean matches(T val){
		return (getFirst().equals(val) || getSecond().equals(val));
	}
	
	public boolean matches(T val1, T val2){
		return (getFirst().equals(val1) || getSecond().equals(val1) ||
				getFirst().equals(val2) || getSecond().equals(val2));
	}

	
}
