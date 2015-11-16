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
import org.semanticweb.owlapi.util.InferredDisjointClassesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;
import org.semanticweb.owlapi.util.InferredSubClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredSubObjectPropertyAxiomGenerator;

public class SingleThread {
	TaskDecopostion td;
	public SingleThread(TaskDecopostion td){
	this.td=td;	
	
	}
	
	public void classify_Ontology() throws Exception{
		
		 OWLOntologyManager man = OWLManager.createOWLOntologyManager();	
		   OWLOntology nonEL = man.createOntology(this.td.getNonELModule());
	
    	 System.out.println("----------------------------------------------");	
    	 System.out.println("  classifying the non_EL ontology with HermiT.....:");
    	 System.out.println("  the non_EL ontology  in single threads 11111111====:"+ nonEL);
		
    	 Reasoner hermit= new Reasoner(nonEL);
		  long t1 = System.currentTimeMillis();
		  hermit.precomputeInferences(InferenceType.CLASS_HIERARCHY);
		  long tHermit  = System.currentTimeMillis()-t1;
		
		     System.out.println("HermiT classifying non_module took  in 1 thread" + tHermit + " milliseconds");
		     
		     
	
		       System.out.println("----------------------------------------------");
		      // System.out.println("创建临时本体所需时间：");
			 long t_create = System.currentTimeMillis();
		        
		    List<InferredAxiomGenerator<? extends OWLAxiom>> gens = new ArrayList<InferredAxiomGenerator<? extends OWLAxiom>>();
			    gens.add(new InferredSubClassAxiomGenerator());
			    gens.add(new InferredDisjointClassesAxiomGenerator());
			    gens.add(new InferredSubObjectPropertyAxiomGenerator());  
			
			    try{
			    
			    OWLOntologyManager m = OWLManager.createOWLOntologyManager();	
			   OWLOntology inferred_Ontology = m.createOntology();
			
		       InferredOntologyGenerator iog = new InferredOntologyGenerator(hermit, gens);
			   iog.fillOntology(m, inferred_Ontology);
			
			
			 
			 OWLOntologyManager	oom=OWLManager.createOWLOntologyManager();
	
			     oom.addAxioms(inferred_Ontology, this.td.getELModule());//剩余的EL加入到非EL模块的推理结果，形成新的本体
			     oom.addAxioms(inferred_Ontology, this.td.getRemainderEL());
			     System.out.println("========inferred_Ontology+remainingEL=========: "+inferred_Ontology);
			     
				
			  
			      long tc=System.currentTimeMillis()-t_create;
			      
			       System.out.println("创建临时本体所需时间： "+tc +"  ms" );
				  System.out.println( "--------------------------------------------------------------");    
				 System.out.println( "合并后的本体公里数数目统计:");
				 System.out.println( inferred_Ontology);
	     
		    System.out.println("  classifying final EL ontology:");
		    long    tLreasoner = System.currentTimeMillis();     
		    classificationWithELK(inferred_Ontology);
		    tLreasoner = System.currentTimeMillis() - tLreasoner;
			System.out.println("ELK classifying the whole ontology: " + tLreasoner + "  milliseconds");
		      
			hermit.dispose();	
			   
			    }catch(Exception e){}
		
			    
			    
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
     	 
//     	    long    tLreasoner = System.currentTimeMillis();
     	    reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
//     	    tLreasoner = System.currentTimeMillis() - tLreasoner;
       //      System.out.println("ELK took " + tLreasoner + "  milliseconds");
             
     	    reasoner.dispose();
     	  }
     	
	
	

}
