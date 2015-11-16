package test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.elk.owlapi.ElkReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.util.InferredAxiomGenerator;
import org.semanticweb.owlapi.util.InferredClassAssertionAxiomGenerator;
import org.semanticweb.owlapi.util.InferredDisjointClassesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentObjectPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;
import org.semanticweb.owlapi.util.InferredPropertyAssertionGenerator;
import org.semanticweb.owlapi.util.InferredSubClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredSubObjectPropertyAxiomGenerator;

public class ReasonerThread extends Thread{
	
	Set<OWLAxiom> remainderEL=new HashSet();
	OWLOntology ontology;
	boolean isEL;

	public  ReasonerThread(OWLOntology ontology,boolean isEL,Set<OWLAxiom> remainderEL){
		this.ontology=ontology;	
		this.isEL=isEL;
		this.remainderEL.addAll(remainderEL);
		
	}
	public void run(){
		if(isEL==true){
			System.out.println("----------------------------------------------");
			System.out.println("  classifying the EL ontology with ELK.....:");
			long    tLreasoner = System.currentTimeMillis();
			classificationWithELK(ontology);
			tLreasoner = System.currentTimeMillis() - tLreasoner;
			System.out.println("ELK classifying EL module took " + tLreasoner + "  milliseconds");
			
		}
		
     if(isEL==false){
    	 System.out.println("----------------------------------------------");	
    	 System.out.println("  classifying the non_EL ontology with HermiT.....:");
    	 System.out.println("  the non_EL ontology  in two threads 222222222222====:"+ ontology);
		 
    	 Reasoner hermit= new Reasoner(ontology);
		  long t1 = System.currentTimeMillis();
		  hermit.precomputeInferences(InferenceType.CLASS_HIERARCHY);
		    long t2 = System.currentTimeMillis() - t1;
		     System.out.println("HermiT classifying non_module took  in 2 threads" + t2 + " milliseconds");
		     
		     
		   
		       System.out.println("----------------------------------------------");
		    
			 long t_create = System.currentTimeMillis();
		        
		    List<InferredAxiomGenerator<? extends OWLAxiom>> gens = new ArrayList<InferredAxiomGenerator<? extends OWLAxiom>>();
			    gens.add(new InferredSubClassAxiomGenerator());
			
			    gens.add(new InferredDisjointClassesAxiomGenerator());
			
			    gens.add(new InferredSubObjectPropertyAxiomGenerator());  
			
			    try{
			    
			    OWLOntologyManager man = OWLManager.createOWLOntologyManager();	
			   OWLOntology inferred_Ontology = man.createOntology();
		
		       InferredOntologyGenerator iog = new InferredOntologyGenerator(hermit, gens);
			   iog.fillOntology(man, inferred_Ontology);
			   System.out.println("分类结果取出完毕 ");
		 
				 System.out.println("推理结果: "+inferred_Ontology);//3827
				 System.out.println("剩余EL: "+this.remainderEL.size());//330
			 
			 OWLOntologyManager	oom=OWLManager.createOWLOntologyManager();
	
			//    oom.addAxioms(inferred_Ontology, this.remainderEL);//剩余的EL加入到非EL模块的推理结果，形成新的本体
			
			
				

			 
			 
			    OWLOntology mergedOnto=oom.createOntology(this.remainderEL);
			    System.out.println("remainderEL本体创建完毕 ");
			    System.out.println("合并前的剩余EL: "+mergedOnto);//330
			   oom.addAxioms(mergedOnto, inferred_Ontology.getAxioms());////推理结果加入到EL模块，形成新的本体
			   System.out.println("合并后: "+mergedOnto);//3905
			   
			 
			 
			 System.out.println("========inferred_Ontology+remainingEL=========: "+inferred_Ontology);//3905
			 
			  
			      long tc=System.currentTimeMillis()-t_create;
			      
			       System.out.println("创建临时本体所需时间： "+tc +"  ms" );
				  System.out.println( "--------------------------------------------------------------");    
				// System.out.println( "合并后的本体公里数数目统计:");
				 // statistics(infOnt);
		       
			  //ELK
				//  System.out.println( "-------------------------------------------------------------------------");
	     
		    System.out.println("  classifying final EL ontology:");
		    long    tLreasoner = System.currentTimeMillis();     
		    classificationWithELK(inferred_Ontology);
		    tLreasoner = System.currentTimeMillis() - tLreasoner;
			System.out.println("ELK classifying remaining EL and non_EL module took " + tLreasoner + "  milliseconds");
		      
			hermit.dispose();	
			   
			    }catch(Exception e){}
		
			    
			    
			    }	
		
	}
	
public  void classificationWithELK(OWLOntology ont){
		
		//OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		
		 Logger.getLogger("org.semanticweb.elk").setLevel(Level.OFF);
		 Logger.getLogger("org.semanticweb.elk").setLevel(Level.ERROR);
		 Logger.getLogger("org.semanticweb.elk.reasoner.indexing").setLevel(Level.ERROR);
		
		
		  // Create an ELK reasoner for ontology.
	    OWLReasonerFactory reasonerFactory = new ElkReasonerFactory();
	    OWLReasoner reasoner = reasonerFactory.createReasoner(ont);
	 // Classify the ontology.
	 
//	    long    tLreasoner = System.currentTimeMillis();
	    reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
//	    tLreasoner = System.currentTimeMillis() - tLreasoner;
  //      System.out.println("ELK took " + tLreasoner + "  milliseconds");
	    
//	    List<InferredAxiomGenerator<? extends OWLAxiom>> gens = new ArrayList<InferredAxiomGenerator<? extends OWLAxiom>>();
//	    gens.add(new InferredSubClassAxiomGenerator());
	
//	    gens.add(new InferredDisjointClassesAxiomGenerator());
	
//	    gens.add(new InferredSubObjectPropertyAxiomGenerator()); 
	    
//	    gens.add(new InferredClassAssertionAxiomGenerator());
//	    gens.add(new InferredDisjointClassesAxiomGenerator());
//	    gens.add(new InferredEquivalentClassAxiomGenerator());
//	    gens.add(new InferredSubObjectPropertyAxiomGenerator());  
//	   gens.add(new InferredEquivalentObjectPropertyAxiomGenerator());
//	    gens.add(new InferredClassAssertionAxiomGenerator());
//	    gens.add(new InferredPropertyAssertionGenerator());
//	 ELK 不支持下面三种公理
//	 gens.add(new InferredObjectPropertyCharacteristicAxiomGenerator());
//	  gens.add(new InferredSubDataPropertyAxiomGenerator());
//	  gens.add(new InferredDataPropertyCharacteristicAxiomGenerator()); // Non EL
//	  gens.add(new InferredEquivalentDataPropertiesAxiomGenerator());
//	 gens.add(new InferredInverseObjectPropertiesAxiomGenerator());
	//   下面的不能实例化
	//    gens.add(new InferredEntityAxiomGenerator());
	//    gens.add(new InferredIndividualAxiomGenerator());
	//    gens.add(new InferredIndividualAxiomGenerator());
	    
	    
	
	    
	    
	    
        
	    reasoner.dispose();
	
	
	
	
}	
}


