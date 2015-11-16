package test;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;

public class ComRsTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		File pro_v117=new File("D:/Ontology/pro_v1.17.obo");//EL
		File pro_v122=new File("D:/Ontology/pro_v1.22.obo");//EL
		File pro_inf160=new File("D:/Ontology/pro-inferred_v16.0.obo");//non_el:8
			
	//	File hrdo_file=new File("E:/Ontology/hrdo.owl");// Problem parsing file:/E:/Ontology/hrdo.owl
	//	File ICD_file=new File("E:/Ontology/ICD9CM.ttl");//Could not load imported ontology:
		//File hino_file=new File("E:/Ontology/hino_vVision_Release__1038.owl");//No! Could not load imported ontology
		//File hugo_file=new File("E:/Ontology/Hugo_July12_2010_vJuly2010.owl");//No! Problem parsing file
		//File clov_file=new File("E:/Ontology/clov2130.owl");//No Could not load imported ontology	
		//	File NPO_file=new File("E:/Ontology/NPOntology01_vunknown.owl");// No! [Axioms: 234994 Logical Axioms: 159992] OutOfMemoryError: Java heap space
		File obi_file=new File("D:/Ontology/merged-obi-comments_v2012-03-29.owl");// No error getPrincipal() outofmemmory
		//File FMA_file=new File("E:/Ontology/fmaOwlFullComponent_2_0.owl");//Could not load imported ontology:
		//File bone_file=new File("E:/Ontology/bonedysplasia.owl");//Could not load imported ontology:
		
		//File cco_file=new File("E:/Ontology/cco_v201.obo");	//5d,java.lang.OutOfMemoryError: Java heap space
      //File GeXO_file=new File("E:/Ontology/GeXO_v1.01.obo");// 5d,not complete
			File NCI_file1=new File("D:/Ontology/ThesaurusInf_13.02dOWL/ThesaurusInferred.owl");//256M outofmemmory  // Tbox: 357054 RBox:13   [Axioms: 1566007 Logical Axioms: 357055]
		
		//File NCI_file2=new File("E:/Ontology/nci/NCI.owl");//156M / Tbox: 130928 RBox:19
			File nci_file4=new File("D:/Ontology/Thesaurus_12.04e-Processed.owl");//  [Axioms: 1188705 Logical Axioms: 182366]Non_EL axioms: 65
			File NCI_file5=new File("D:/Ontology/NCITNCBO_14_03e.owl");//[Axioms: 1390339 Logical Axioms: 226038]non-el 67
			
			File NCI_file3=new File("D:/Ontology/Thesaurus_10_03.owl");// [Axioms: 1093733 Logical Axioms: 111739] TBox: 111732 RBox: 19  Non_EL axioms: 4541
			
			//File biomodel_file=new File("E:/Ontology/biomodels-21.owl");//outofmemmory
			//File OBO_file=new File("E:/Ontology/OBO.owl");	//all axioms: 20157    Non_EL axiom: 1232 non_EL module:20094
			
			
		
		
		//	File go_file=new File("D:/Ontology/go_daily-termdb.owl/go_daily-termdb.owl");//EL  
	// File flopo_file=new File("E:/Ontology/flopo-classified.owl");//classified   EL ontology  [Axioms: 87708 Logical Axioms: 31902]
		File chebi_file=new File("E:/Ontology/chebi.obo");//EL ontology [Axioms: 641802 Logical Axioms: 118878]分解时间： 1269046ms
			File ICNP_file=new File("D:/Ontology/ICNP2011_v2011.owl");//ok
	//	File protein2_file=new File("E:/Ontology/pro_reasoned_v34.0.obo");//ok
			File galen_file=new File("D:/Ontology/full-galen_v1.1.owl");// ok  
		
		File DermLex_file=new File("E:/Ontology1/DermLex_Ver_1_0.owl");// ok
	//	File EDAM_file=new File("E:/Ontology1/EDAM_1.3.owl");// ok	
//		File idoden_file=new File("E:/Ontology1/idoden_beta0.15.owl");/ok
		File ero_file=new File("D:/Ontology/ero.owl");//ok
		File HuPSON_phenotype_file=new File("E:/Ontology1/HuPSON_v092013_merged.owl");//ok

		// 4.27  new ontology
	//  // File bone_file=new File("E:/Ontology/bonedysplasia.owl");//????:
	//	File bao_file=new File("E:/Ontology/bao_complete.owl");// 感觉有问题，逻辑公里数目小于分解的取得公里数目，是不是逻辑返回的逻辑公里中不包含引入的公里
	//	File bao_file=new File("E:/Ontology1/bao_complete.owl");
	//	File amphibian_file=new File("E:/Ontology1/amphibian_taxonomy.OBO");//EL 
	//	File BIRNLex_file=new File("E:/Ontology1/BIRNLex.owl");//EL
	//	File BrendaTissue_file=new File("E:/Ontology1/BrendaTissue.OBO");//EL
	
	//	File BrendaTissue_file=new File("E:/Ontology1/BrendaTissue.OBO");//EL
	//	File brucellosis_file=new File("E:/Ontology1/brucellosis.owl");// imported
	//	File cmo_xp_file=new File("E:/Ontology1/cmo-xp.obo");//EL
		File CTCAE_xp_file=new File("E:/Ontology1/CTCAE_4.03_2010-06-14.owl");//imorted
	//	File dcm_file=new File("E:/Ontology1/dcm.owl");// imported
	//	File EFO_file=new File("E:/Ontology1/EFO_inferred.owl");// Problem parsing file:/E:/Ontology1/EFO_inferred.owl	
	//	File EMAP_file=new File("E:/Ontology1/EMAP.obo");//ok
//		
		
		
		
	//	File HL7_CUIS_file=new File("E:/Ontology1/HL7-CUIS.owl");//EL
	//	File hao_file=new File("E:/Ontology1/hao.owl");//EL
	//	File human_file=new File("E:/Ontology1/human-dev-anat-abstract.obo");//EL
	//	File human_dev_file=new File("E:/Ontology1/human-dev-anat-staged_v1.3.obo");//EL
	//	File human_phenotype_file=new File("E:/Ontology1/human-phenotype-ontology.obo");//EL
	//	File HuPSON_phenotype_file=new File("E:/Ontology1/HuPSON_v092013_merged.owl");//ok
	//	File iceci_file=new File("E:/Ontology1/iceci.owl");//EL
	
	//	File IDOMAL_file=new File("E:/Ontology1/IDOMAL_1.3.7.obo");//EL
	//	File KB_Bio_file=new File("E:/Ontology1/KB_Bio_101.ofn");//   java.lang.OutOfMemoryError: Java heap space
	//	File MaHCO_file=new File("E:/Ontology1/MaHCO.owl");//需要引入外部本体。逻辑公理少于AD中的公理？？？？
	//	File mammalian_file=new File("E:/Ontology1/mammalian_phenotype.obo");//EL
	//	File medaka_file=new File("E:/Ontology1/medaka_ontology.obo");//EL
	//	File miro_file=new File("E:/Ontology1/miro_release.obo");//EL
	//	File nif_file=new File("E:/Ontology1/nif.owl");//Could not load imported ontology:
	//File NIF_file=new File("E:/Ontology1/NIF-Cell.owl");//需要引入外部本体。逻辑公理少于AD中的公理？？？？
	       //	File NIFDys_file=new File("E:/Ontology1/NIF-Dysfunction.owl");// 需要引入外部本体。逻辑公理少于AD中的公理？？？？
   //   File NIGO_file=new File("E:/Ontology1/NIGO.obo");//EL
	//	File ontovip_file=new File("E:/Ontology1/ontovip.owl");//org.semanticweb.HermiT.datatypes.UnsupportedDatatypeException
		
	//	File profile=new File("E:/Ontology1/pro_reasoned.obo");//好像重复
	//	File psi_file=new File("E:/Ontology1/psi-ms.obo");//EL
	//	File Radlex_file=new File("E:/Ontology1/Radlex311.owl");// Problem parsing file:
	//	File rat_file=new File("E:/Ontology1/rat_strain.obo");//EL
	//	File rexo_file=new File("E:/Ontology1/rexo.obo");//太大，待验证
	//	File FAOnt_file=new File("E:/Ontology1/V3.0FAOntology.rdf-xml.owl");//EL
	//	File FAOnt_file=new File("E:/Ontology1/V3.0FAOntology.rdf-xml.owl");
		
		File OBI10871_file=new File("D:/Ontology/obi_v1.0.871.owl");
		File OBI10967_file=new File("D:/Ontology/obi_v1.0.967.owl");
		File OBI101213_file=new File("D:/Ontology/OBI_v1.0.1213.owl");
		File OBI101423_file=new File("D:/Ontology/OBI-noanno_v1.0.1423.owl");
		File OBI20110420_file=new File("D:/Ontology/merged-obi-comments_v2011-04-20.owl");
		File obi_v20140329_file=new File("D:/Ontology/obi_v_2014-03-29.owl");
		File obi_v20120701_file=new File("D:/Ontology/merged-obi-comments-v2012-07-01.owl");
		File obi_v20111213_file=new File("D:/Ontology/obi_woIEDB_comments_v2011-12-13.owl");
		File obi_v20110720_file=new File("D:/Ontology/merged-obi-comments_v2011-07-20.owl");
		File obi_v20101026_file=new File("D:/Ontology/merged-obi_v2010-10-26_Vancouver_2010_release_RC2.owl");
		File obi_v20091106_file=new File("D:/Ontology/merged-obi_v2009-11-06_Philly_(aka_version_1.0)_Release_Candidate.owl");

		
		File pizza_file=new File("D:/Ontology/pizza.owl");
		File people_file=new File("D:/Ontology/people.owl");
		
		System.out.println("loading ontology....");
		 OWLOntologyManager	m=OWLManager.createOWLOntologyManager();
	     OWLOntology o=m.loadOntologyFromOntologyDocument(NCI_file5);
	     System.out.println("loading completed!");
	     System.out.println("original ontology:");
	     
	     System.out.println(o);
	     System.out.println( "class: "+o.getClassesInSignature().size());
	     System.out.println( "class with imported classes: "+o.getClassesInSignature(true).size());
	     System.out.println( "logical axioms: "+o.getLogicalAxiomCount());
	     System.out.println("-----------------------------------");
	     System.out.println("TBox: "+o.getTBoxAxioms(true).size());
	     System.out.println("RBox: "+o.getRBoxAxioms(true).size()) ;

	 
	  System.out.println("----------------------------------------------");	
    	 System.out.println("  Non_EL axioms: "+  getNon_ELAxiom(o).size());
	  
	  
	  
	     System.out.println("----------------------------------------------");	
    	 System.out.println("  classifying the original ontology with HermiT.....:");
		  Reasoner hermit= new Reasoner(o);
		  long tHermit = System.currentTimeMillis();
		    // hermit.classifyClasses();
		  hermit.precomputeInferences(InferenceType.CLASS_HIERARCHY);
		     tHermit = System.currentTimeMillis() - tHermit;
		     System.out.println("HermiT classifying original ontology： " + tHermit + " milliseconds");
		     
		     hermit.dispose();
	   
	  /*  */
	     
    	 
    	 
    	
	   /*  
	    
	     System.out.println("starting decomposing ontology....");
	     TaskDecopostion td=new TaskDecopostion(o);
	     System.out.println("decomposition completed!");
	     
	//     System.out.println("-----------------------------------");
	//     System.out.println("TaughtLogical axioms :  " +td.getTaughtCount());
	     
	//     System.out.println("-----------------------------------");
	 //    System.out.println("Axioms Count in AD:  "+ td.getAxiomsAfterAD());
	  
	      
	     System.out.println("-----------------------------------");
	     System.out.println("totals Atoms: "+td.adt.getAtoms().size());
	     System.out.println("non_EL Atoms: "+td.getnonELatomList().size());
	    
	     System.out.println("-----------------------------------");
	     System.out.println("all axioms: "+(o.getTBoxAxioms(true).size()+o.getRBoxAxioms(true).size()) +  "    Non_EL axiom: "+td.getNon_ELAxiom().size());
	     
	   
	     
	     
	     System.out.println("-----------------------------------");
	     System.out.println("non_EL module:"+td.getNonELModule().size());
	     
	     System.out.println("-----------------------------------");
	     System.out.println("EL module:"+td.getELModule().size());
	     System.out.println("-----------------------------------");
	     System.out.println("remaidering EL module:"+td.getRemainderEL().size());
	     
	     System.out.println("-----------------------------------");
	     System.out.println("checking EL module......");
	     
	  
	     
	     
	       
	     
	     
	    
	     System.out.println("-----------------------------------");
	     System.out.println("two threads:");
	     try{
			    
			    OWLOntologyManager man = OWLManager.createOWLOntologyManager();	
			   OWLOntology non_EL_Ontology = man.createOntology(td.getNonELModule());
				//   man.addAxioms(non_EL_Ontology, td.getNonELModule());
				   System.out.println("-----non_EL_Ontology------: "+ non_EL_Ontology);
				   
				   OWLOntologyManager manage = OWLManager.createOWLOntologyManager();	
				   OWLOntology el_ontology = manage.createOntology(td.getELModule());
				 //  manage.addAxioms(el_ontology, td.getELModule());
				   System.out.println("------el_ontology-----: "+ el_ontology);
				   
				   ReasonerThread non_EL_thread=new ReasonerThread(non_EL_Ontology,false,td.getRemainderEL());
				   ReasonerThread EL_thread=new ReasonerThread(el_ontology,true,td.getRemainderEL());
				   
				   non_EL_thread.start();
				   EL_thread.start();
			
			
	   
		
	    
	     
	     
	     }catch(Exception e){}
		
		
	     
	     /*
	     System.out.println("-----------------------------------");
	     System.out.println("single thread:");
	     SingleThread st=new SingleThread(td);
	     st.classify_Ontology();
	     System.out.println();
	    */
	     System.out.println("program completed!");
	     
	    
	}// end of main 
	
	   public static  Set<OWLAxiom>  getNon_ELAxiom(OWLOntology ont)
		  {
			
			 
			
		
			
			   Set<OWLAxiom> nonELaxiom=new HashSet();
		    int n = 0;
		    ELAxiomVisitor visitor = new ELAxiomVisitor();
		    for (OWLAxiom ax : ont.getTBoxAxioms(true)) {
		      ax.accept(visitor);
		      if (!visitor.isEL())//如果不是EL公理保存在NonELaxiom中
		      {   n++;
		      nonELaxiom.add(ax);
		      }
		    }
		    for (OWLAxiom ax : ont.getRBoxAxioms(true)) {
		      ax.accept(visitor);
		      if (!visitor.isEL())//如果不是EL公理保存在NonELaxiom中
		      {
		    	  n++;
		      nonELaxiom.add(ax);
		     }
		    } 
		
			return nonELaxiom;

		  }	
	
	
	
	
	
	
	

}
