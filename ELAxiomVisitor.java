package test;

import java.util.Iterator;
import java.util.Set;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;


public class ELAxiomVisitor implements OWLAxiomVisitor{
	
	private boolean isEL;

	  public boolean isEL()
	  {
	    return this.isEL;
	  }

	  public boolean isInFragment() {
	    return this.isEL;
	  }

	  public void visit(OWLSubClassOfAxiom axiom)
	  {
	    ELClassExpressionVisitor classExpVisitor = new ELClassExpressionVisitor();
	    OWLClassExpression classExp = axiom.getSubClass();
	    classExp.accept(classExpVisitor);

	    if (classExpVisitor.getIsEL()) {
	      classExp = axiom.getSuperClass();

	      classExp.accept(classExpVisitor);
	    }

	    this.isEL = classExpVisitor.getIsEL();
	  }

	  public void visit(OWLEquivalentClassesAxiom axiom)
	  {
	    Set subClassOfAxioms = axiom.asOWLSubClassOfAxioms();

	    Iterator iterator = subClassOfAxioms.iterator();
	    OWLSubClassOfAxiom oneSubClassOfAxiom = (OWLSubClassOfAxiom)iterator.next();
	    oneSubClassOfAxiom.accept(this);
	    boolean isELSoFar = this.isEL;

	    while ((iterator.hasNext()) && (isELSoFar)) {
	      oneSubClassOfAxiom = (OWLSubClassOfAxiom)iterator.next();
	      oneSubClassOfAxiom.accept(this);
	      isELSoFar = (isELSoFar) && (this.isEL);
	    }
	    this.isEL = isELSoFar;
	  }

	  public void visit(OWLDisjointClassesAxiom axiom) {
	    Set exps = axiom.getClassExpressions();

	    Iterator iterator = exps.iterator();

	    boolean allEL = true;

	    ELClassExpressionVisitor classExpVisitor = new ELClassExpressionVisitor();

	    while ((allEL & iterator.hasNext())) {
	      OWLClassExpression exp = (OWLClassExpression)iterator.next();

	      exp.accept(classExpVisitor);
	      allEL = classExpVisitor.getIsEL();
	    }

	    this.isEL = allEL;
	  }

	  public void visit(OWLSubObjectPropertyOfAxiom axiom) {
	    this.isEL = ((!((OWLObjectPropertyExpression)axiom.getSubProperty()).isAnonymous()) && (!((OWLObjectPropertyExpression)axiom.getSuperProperty()).isAnonymous()));
	  }

	  public void visit(OWLSubPropertyChainOfAxiom axiom) {
	    this.isEL = true;
	    for (OWLObjectPropertyExpression prop : axiom.getPropertyChain()) {
	      this.isEL = ((this.isEL) && (!prop.isAnonymous()));
	    }
	    this.isEL = ((this.isEL) && (!axiom.getSuperProperty().isAnonymous()));
	  }

	  public void visit(OWLSubDataPropertyOfAxiom axiom) {
	    this.isEL = ((!((OWLDataPropertyExpression)axiom.getSubProperty()).isAnonymous()) && (!((OWLDataPropertyExpression)axiom.getSuperProperty()).isAnonymous()));
	  }

	  public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
	    this.isEL = true;
	    for (OWLObjectPropertyExpression prop : axiom.getProperties())
	      this.isEL = ((this.isEL) && (!prop.isAnonymous()));
	  }

	  public void visit(OWLEquivalentDataPropertiesAxiom axiom)
	  {
	    this.isEL = true;
	    for (OWLDataPropertyExpression prop : axiom.getProperties())
	      this.isEL = ((this.isEL) && (!prop.isAnonymous()));
	  }

	  public void visit(OWLTransitiveObjectPropertyAxiom axiom)
	  {
	    this.isEL = (!((OWLObjectPropertyExpression)axiom.getProperty()).isAnonymous());
	  }

	  public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
	    this.isEL = (!((OWLObjectPropertyExpression)axiom.getProperty()).isAnonymous());
	  }

	  public void visit(OWLObjectPropertyDomainAxiom axiom) {
	    this.isEL = (!((OWLObjectPropertyExpression)axiom.getProperty()).isAnonymous());
	    if (this.isEL) {
	      ELClassExpressionVisitor classExpVisitor = new ELClassExpressionVisitor();
	      axiom.getDomain().accept(classExpVisitor);
	      this.isEL = classExpVisitor.getIsEL();
	    }
	  }

	  public void visit(OWLDataPropertyDomainAxiom axiom) {
	    this.isEL = (!((OWLDataPropertyExpression)axiom.getProperty()).isAnonymous());
	    if (this.isEL) {
	      ELClassExpressionVisitor classExpVisitor = new ELClassExpressionVisitor();
	      axiom.getDomain().accept(classExpVisitor);
	      this.isEL = classExpVisitor.getIsEL();
	    }
	  }

	  public void visit(OWLObjectPropertyRangeAxiom axiom) {
	    this.isEL = (!((OWLObjectPropertyExpression)axiom.getProperty()).isAnonymous());
	    if (this.isEL) {
	      ELClassExpressionVisitor classExpVisitor = new ELClassExpressionVisitor();
	      ((OWLClassExpression)axiom.getRange()).accept(classExpVisitor);
	      this.isEL = classExpVisitor.getIsEL();
	    }
	  }

	  public void visit(OWLDataPropertyRangeAxiom axiom) {
	    this.isEL = (!((OWLDataPropertyExpression)axiom.getProperty()).isAnonymous());
	  }

	  public void visit(OWLFunctionalDataPropertyAxiom axiom) {
	    this.isEL = (!((OWLDataPropertyExpression)axiom.getProperty()).isAnonymous());
	  }

	  public void visit(OWLHasKeyAxiom axiom) {
	    this.isEL = true;
	    for (OWLPropertyExpression prop : axiom.getPropertyExpressions()) {
	      this.isEL = ((this.isEL) && (!prop.isAnonymous()));
	    }
	    if (this.isEL) {
	      ELClassExpressionVisitor classExpVisitor = new ELClassExpressionVisitor();
	      axiom.getClassExpression().accept(classExpVisitor);
	      this.isEL = classExpVisitor.getIsEL();
	    }
	  }

	  public void visit(OWLDeclarationAxiom axiom) {
	    this.isEL = true;
	  }

	  public void visit(OWLAsymmetricObjectPropertyAxiom axiom)
	  {
	    this.isEL = false;
	  }

	  public void visit(OWLDisjointDataPropertiesAxiom axiom) {
	    this.isEL = false;
	  }

	  public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
	    this.isEL = false;
	  }

	  public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
	    this.isEL = false;
	  }

	  public void visit(OWLDisjointUnionAxiom axiom) {
	    this.isEL = false;
	  }

	  public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
	    this.isEL = false;
	  }

	  public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
	    this.isEL = false;
	  }

	  public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
	    this.isEL = false;
	  }

	  public void visit(OWLInverseObjectPropertiesAxiom axiom) {
	    this.isEL = false;
	  }

	  public void visit(OWLDatatypeDefinitionAxiom axiom) {
	    this.isEL = false;
	  }

	  public void visit(SWRLRule axiom)
	  {
	    this.isEL = false;
	  }

	  public void visit(OWLAnnotationAssertionAxiom axiom)
	  {
	    this.isEL = false;
	  }

	  public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
	    this.isEL = false;
	  }

	  public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
	    this.isEL = false;
	  }

	  public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
	    this.isEL = false;
	  }

	  public void visit(OWLClassAssertionAxiom axiom) {
	    this.isEL = false;
	  }

	  public void visit(OWLObjectPropertyAssertionAxiom axiom) {
	    this.isEL = false;
	  }

	  public void visit(OWLDataPropertyAssertionAxiom axiom) {
	    this.isEL = false;
	  }

	  public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
	    this.isEL = false;
	  }

	  public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
	    this.isEL = false;
	  }

	  public void visit(OWLSameIndividualAxiom axiom) {
	    this.isEL = false;
	  }

	  public void visit(OWLDifferentIndividualsAxiom axiom) {
	    this.isEL = false;
	  }

	  protected class ELClassExpressionVisitor implements OWLClassExpressionVisitor
	  {
	    private boolean isEL;

	    protected ELClassExpressionVisitor()
	    {
	    }

	    public boolean getIsEL()
	    {
	      return this.isEL;
	    }

	    public void visit(OWLClass exp) {
	      this.isEL = (!exp.isOWLNothing());
	    }

	    public void visit(OWLObjectSomeValuesFrom exp) {
	      OWLObjectPropertyExpression propertyExp = (OWLObjectPropertyExpression)exp.getProperty();
	      OWLClassExpression classExp = (OWLClassExpression)exp.getFiller();
	      classExp.accept(this);
	      this.isEL = ((!propertyExp.isAnonymous()) && (getIsEL()));
	    }

	    public void visit(OWLDataSomeValuesFrom exp) {
	      this.isEL = (!((OWLDataPropertyExpression)exp.getProperty()).isAnonymous());
	    }

	    public void visit(OWLDataHasValue exp) {
	      this.isEL = (!((OWLDataPropertyExpression)exp.getProperty()).isAnonymous());
	    }

	    public void visit(OWLObjectHasValue exp) {
	      this.isEL = (!((OWLObjectPropertyExpression)exp.getProperty()).isAnonymous());
	    }

	    public void visit(OWLObjectHasSelf exp) {
	      this.isEL = (!((OWLObjectPropertyExpression)exp.getProperty()).isAnonymous());
	    }

	    public void visit(OWLObjectOneOf exp) {
	      this.isEL = (exp.getIndividuals().size() < 2);
	    }

	    public void visit(OWLDataOneOf exp) {
	      this.isEL = (exp.getValues().size() < 2);
	    }

	    public void visit(OWLObjectIntersectionOf exp) {
	      Set conjuncts = exp.asConjunctSet();
	      Iterator iterator = conjuncts.iterator();
	      OWLClassExpression classExp = (OWLClassExpression)iterator.next();

	      classExp.accept(this);
	      boolean allELSoFar = getIsEL();

	      while ((iterator.hasNext()) && (allELSoFar)) {
	        classExp = (OWLClassExpression)iterator.next();
	        classExp.accept(this);
	        allELSoFar = (allELSoFar) && (getIsEL());
	      }
	      this.isEL = allELSoFar;
	    }

	    public void visit(OWLDataIntersectionOf exp)
	    {
	      this.isEL = true;
	    }

	    public void visit(OWLObjectUnionOf exp)
	    {
	      this.isEL = false;
	    }

	    public void visit(OWLObjectComplementOf exp)
	    {
	      this.isEL = false;
	    }

	    public void visit(OWLObjectAllValuesFrom exp)
	    {
	      this.isEL = false;
	    }

	    public void visit(OWLObjectMinCardinality exp)
	    {
	      this.isEL = false;
	    }

	    public void visit(OWLObjectExactCardinality exp)
	    {
	      this.isEL = false;
	    }

	    public void visit(OWLObjectMaxCardinality exp)
	    {
	      this.isEL = false;
	    }

	    public void visit(OWLDataAllValuesFrom exp)
	    {
	      this.isEL = false;
	    }

	    public void visit(OWLDataMinCardinality exp)
	    {
	      this.isEL = false;
	    }

	    public void visit(OWLDataExactCardinality exp)
	    {
	      this.isEL = false;
	    }

	    public void visit(OWLDataMaxCardinality exp)
	    {
	      this.isEL = false;
	    }
	  }
	

}
