package test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.atomicdecomposition.Atom;
import uk.ac.manchester.cs.atomicdecomposition.AtomicDecomposerOWLAPITOOLS;
import uk.ac.manchester.cs.owlapi.modularity.ModuleType;

public class TaskDecopostion {
	OWLOntology originalOnt;
	OWLOntology EL_Ont;
	OWLOntology non_EL_Ont;
	OWLOntology EL_on_non_EL;
	
	// Set<Atom> nonELatomList=new HashSet();
	// Set<OWLAxiom> nonELaxiom=new HashSet();
	 Set<OWLEntity> nonELentity=new HashSet();
	
	AtomicDecomposerOWLAPITOOLS adt;
	
	public TaskDecopostion(OWLOntology originalOnt){
		this.originalOnt=originalOnt;
		long startTime=System.currentTimeMillis();   //获取开始时间
		this.adt=new AtomicDecomposerOWLAPITOOLS(originalOnt,ModuleType.STAR);	
		long endTime=System.currentTimeMillis(); //获取结束时间
		  System.out.println("分解时间： "+(endTime-startTime)+"ms");
	}
	

	
	
	
	
public int getTaughtCount(){
	
	return adt.getTautologies().size();
	
	
}
public int getAxiomsAfterAD(){
	
	Set <Atom> s=adt.getAtoms();
	Iterator<Atom> it=s.iterator();
	int i=0;
	while(it.hasNext()){
		i=i+it.next().getAxioms().size();
		
	}
	
	return i;
	
}




	   public Set<OWLAxiom>  getNon_ELAxiom()
	  {
		
		 
		
	 //   System.out.println("Signature original: " + ontlgy.getSignature().size());
	   // System.out.println("Original relevant ontology TBoxaxioms: " + (ontlgy.getTBoxAxioms(true).size()));

	    //System.out.println("Original relevant ontology RBoxaxioms: " + ( ontlgy.getRBoxAxioms(true).size()));
	    //System.out.println("Original relevant ontology axioms: " + (ontlgy.getTBoxAxioms(true).size() + ontlgy.getRBoxAxioms(true).size()));
		
		   Set<OWLAxiom> nonELaxiom=new HashSet();
	    int n = 0;
	    ELAxiomVisitor visitor = new ELAxiomVisitor();
	    for (OWLAxiom ax : originalOnt.getTBoxAxioms(true)) {
	      ax.accept(visitor);
	      if (!visitor.isEL())//如果不是EL公理保存在NonELaxiom中
	      {   n++;
	      nonELaxiom.add(ax);
	      }
	    }
	    for (OWLAxiom ax : originalOnt.getRBoxAxioms(true)) {
	      ax.accept(visitor);
	      if (!visitor.isEL())//如果不是EL公理保存在NonELaxiom中
	      {
	    	  n++;
	      nonELaxiom.add(ax);
	     }
	    } 
	 // System.out.println("\tof which " + n + " are EL\n");
	//	System.out.println("nonELaxiom：   "+ nonELaxiom.size());
	    
		return nonELaxiom;

	  }	
	
	public  Set<Atom> getnonELatomList(){
		Set<Atom> nonELatomList=new HashSet();
		Iterator<OWLAxiom> nonEL=this.getNon_ELAxiom().iterator();
		 Atom at=null;
		   while(nonEL.hasNext()){//查询非EL公理列表
			   OWLAxiom axiom=nonEL.next();
            at=adt.getAtomForAxiom(axiom);//得到包含非EL的原子
			 nonELatomList.add(at); // add NonELatom to NoELatom;
		}
	 return nonELatomList;
	}

	
	
 public Set<OWLAxiom> getNonELModule(){
	//System.out.println("calling getNonELModule...");
	//计算非EL原子的主理想
	Iterator<Atom> it=this.getnonELatomList().iterator();

	//System.out.println("nonELatomList size====="+it.size());
	Set<OWLAxiom> NonELModule=new HashSet<OWLAxiom>();
	int i=0;
	
	//while(it.hasNext()){
    for(Atom a:this.getnonELatomList()){
		
	//	NonELModule.addAll(adt.getPrincipalIdeal(it.next()));
    	NonELModule.addAll(adt.getPrincipalIdeal(a));
	//	System.out.println("adt.getPrincipalIdeal(it.next())="+ adt.getPrincipalIdeal(it.next()));
	//	Set<OWLAxiom> s=new HashSet<OWLAxiom>();
		
	//	adt.getPrincipalIdeal(it.next());
		
		}
	return NonELModule;
	
 }

public Set<OWLAxiom> getELModule(){
	Set<Atom> non_EL_atoms=this.getnonELatomList();
	Set<OWLAxiom> ELAxioms=new HashSet();
	Set<Atom> ELAtoms=new HashSet();
	Set<Atom> top_atom=adt.getTopAtoms();
	boolean containingEL=true;
	
	for(Atom at:  top_atom)
	{
		containingEL=true;
			for(Atom non_EL_atom: non_EL_atoms)
		   {
		   if(adt.getDependencies(at).contains(non_EL_atom))
		      {
			   containingEL=false;
			  			   break;
	     	   }
		   }// end of inner for
		  if(containingEL==true)
		     {
			  ELAtoms.addAll(adt.getDependencies(at));   
	         }
		 }// end of outside for
	
	for(Atom at: ELAtoms)
	  {
		ELAxioms.addAll(at.getAxioms());
	   }
	return ELAxioms;
		
  }	
 
 public Set<OWLAxiom> getRemainderEL(){
	
	Set<OWLAxiom> all_axiom=new HashSet<OWLAxiom>();
	all_axiom.addAll(originalOnt.getTBoxAxioms(true));
	System.out.println("TBox:"+originalOnt.getTBoxAxioms(true).size());
	all_axiom.addAll(originalOnt.getRBoxAxioms(true));
	System.out.println("RBox:"+originalOnt.getRBoxAxioms(true).size());
	System.out.println("all the axiom:"+all_axiom.size());
	all_axiom.removeAll(getNonELModule());//remove NonEL
	all_axiom.removeAll(getELModule());//remove EL
	//System.out.println("remainder EL:"+all_axiom.size());

	//测试TBox和ABox的交集
	/*
	Set <OWLAxiom> t=originalOnt.getTBoxAxioms(true);
	Set <OWLAxiom> r=originalOnt.getRBoxAxioms(true);
	for(OWLAxiom a:  r)
	{
		   if(t.contains(a))
		      {
				System.out.println(" in both T and R:  "+a);

	     	   }
		   }
		*/ 
	
	

	return all_axiom;
		
	
	}




	static public Set<OWLAxiom> getAxioms(OWLOntology ontology){
		
		Set<OWLAxiom> set=new HashSet<OWLAxiom>();
		set.addAll(ontology.getTBoxAxioms(true));
		set.addAll(ontology.getRBoxAxioms(true));
		return set;
	}
	
	
	
	static public OWLOntology getOntology(Set<OWLAxiom> set) throws OWLOntologyCreationException,
	OWLOntologyChangeException{
		//try{
		OWLOntologyManager	m=OWLManager.createOWLOntologyManager(); 
		OWLOntology ontology=m.createOntology(set);
		//}
		//catch(OWLOntologyCreationException e){System.out.println(e);} 
		//catch(OWLOntologyChangeException e){System.out.println(e);}
		
		return ontology;
	}
	
	

	
	
	

}
