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
	
	public static void main (String[] args){
		GenericDomino<Integer> dom1 = new GenericDomino<Integer>(5,5);
		GenericDomino<Character> dom2 = new GenericDomino<Character>('a','a');
		GenericDomino<Integer> dom3 = new GenericDomino<Integer>(1,0);
		GenericDomino<Integer> dom4 = new GenericDomino<Integer>(0,1);
		GenericPair<Integer> dom6 = new GenericPair<Integer>(0,1);
		System.out.println(dom1);
		System.out.println(dom2.getFirst());
		System.out.println(dom2.getSecond());
		
		System.out.println(dom2.equals(dom3));
		System.out.println(dom2.equals(dom2));
		System.out.println(dom3.equals(dom4));
		System.out.println(dom4.equals(dom6));
	}
	
}
