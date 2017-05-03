package generics.abstractions;


public abstract class PairAbstraction implements PairSpecification{

	
	public abstract Object getFirst();
	
	public abstract Object getSecond();
	
	public abstract void setFirst(Object o);
	
	public abstract void setSecond(Object o);
	
	public	void swap(){
		
		Object temp = getFirst();
		setFirst(getSecond());
		setSecond(temp);
	}
	
	public String toString (){
		
		return "("+getFirst()+", "+getSecond()+")";
		
	}
	   
	public boolean equals(Object obj){
			
		 if (this == obj)
	           return true;
		 if (obj == null)
	           return false;
		 if (!(obj instanceof PairSpecification))
	           return false;

		 PairAbstraction other = (PairAbstraction) obj;

		 return (other.getFirst().equals(getFirst()) && other.getSecond().equals(getSecond()));
			
	 }
}
