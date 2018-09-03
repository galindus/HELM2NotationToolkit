/**
 * *****************************************************************************
 * Copyright C 2015, The Pistoia Alliance
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *****************************************************************************
 */
package org.helm.notation2.tools;

import java.io.IOException;
import java.util.List;

import org.helm.chemtoolkit.AbstractChemistryManipulator;
import org.helm.chemtoolkit.AbstractMolecule;
import org.helm.chemtoolkit.CTKException;
import org.helm.chemtoolkit.CTKSmilesException;
import org.helm.chemtoolkit.cdk.CDKManipulator;
import org.helm.notation2.Attachment;
import org.helm.notation2.Chemistry;
import org.helm.notation2.Monomer;
import org.helm.notation2.MonomerFactory;
import org.helm.notation2.exception.BuilderMoleculeException;
import org.helm.notation2.exception.ChemistryException;
import org.helm.notation2.exception.HELM2HandledException;
import org.helm.notation2.exception.MonomerLoadingException;
import org.helm.notation2.exception.NotationException;
import org.helm.notation2.parser.notation.HELM2Notation;
import org.helm.notation2.parser.notation.polymer.ChemEntity;
import org.helm.notation2.parser.notation.polymer.PolymerNotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SMILES class to generate SMILES
 *
 *
 * @author hecht
 */
public final class SMILES {

	/** The Logger for this class */
	private static final Logger LOG = LoggerFactory.getLogger(SMILES.class);

	/**
	 * method to generate smiles for the whole HELMNotation
	 *
	 * @param helm2notation
	 *            input HELMNotation
	 * @return smiles for the whole HELMNotation
	 * @throws BuilderMoleculeException
	 *             if the molecule can't be built
	 * @throws CTKException
	 *             general ChemToolKit exception passed to HELMToolKit
	 * @throws ChemistryException
	 *             if the Chemistry Engine can not be initialized
	 */
	public static String getSMILESForAll(HELM2Notation helm2notation)
			throws BuilderMoleculeException, CTKException, ChemistryException {
		/* Build Molecues */
		LOG.debug("Build single molecule(s)");
		List<AbstractMolecule> molecules = BuilderMolecule.buildMoleculefromPolymers(helm2notation.getListOfPolymers(),
				HELM2NotationUtils.getAllEdgeConnections(helm2notation.getListOfConnections()));
		/* get for every molecule the smiles */
		LOG.debug("Built single molecule(s)");
		StringBuffer sb = new StringBuffer();
		for (AbstractMolecule molecule : molecules) {
			molecule = BuilderMolecule.mergeRgroups(molecule);
			sb.append(Chemistry.getInstance().getManipulator().convertMolecule(molecule,
					AbstractChemistryManipulator.StType.SMILES) + ".");
		}
		sb.setLength(sb.length() - 1);
		LOG.debug("SMILES-All :" + sb.toString());
		return sb.toString();
	}

	/**
	 * method to generate canonical smiles for the whole HELMNotation
	 *
	 * @param helm2notation
	 *            input HELMNotation
	 * @return canonical smiles for the whole HELMNotation
	 * @throws BuilderMoleculeException
	 *             if the molecule can't be built
	 * @throws CTKSmilesException
	 *             if it contains an invalid smiles
	 * @throws CTKException
	 *             general ChemToolKit exception passed to HELMToolKit
	 * @throws NotationException
	 *             if notation is not valid
	 * @throws ChemistryException
	 *             if the Chemistry Engine can not be initialized
	 */
	public static String getCanonicalSMILESForAll(HELM2Notation helm2notation)
			throws BuilderMoleculeException, CTKSmilesException, CTKException, NotationException, ChemistryException {
		LOG.debug("Build single molecule(s)");
		List<AbstractMolecule> molecules = BuilderMolecule.buildMoleculefromPolymers(helm2notation.getListOfPolymers(),
				helm2notation.getListOfConnections());
		LOG.debug("Built single molecule(s)");
		/* get for every molecule the canonical smiles */
		StringBuffer sb = new StringBuffer();
		for (AbstractMolecule molecule : molecules) {
			molecule = BuilderMolecule.mergeRgroups(molecule);
			sb.append(Chemistry.getInstance().getManipulator().canonicalize(Chemistry.getInstance().getManipulator()
					.convertMolecule(molecule, AbstractChemistryManipulator.StType.SMILES)) + ".");
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * method if the any of the given PolymerNotation contains generic
	 * structures
	 *
	 * @param polymers
	 *            list of polymernotations
	 * @return true, if it contains generic structure, false otherwise
	 * @throws HELM2HandledException
	 *             if it contains HELM2 specific features, so that it can not be
	 *             casted to HELM1 Format
	 * @throws ChemistryException
	 *             if chemistry engine can not be initialized
	 * @throws IOException
	 *             if monomer can not be read by chemistry
	 * @throws CTKException
	 *             general ChemToolKit exception passed to HELMToolKit
	 */
	public static boolean containsGenericStructurePolymer(List<PolymerNotation> polymers)
			throws HELM2HandledException, ChemistryException, IOException, CTKException {
		for (PolymerNotation polymer : polymers) {
			if (polymer.getPolymerID() instanceof ChemEntity) {
				Monomer monomer = MethodsMonomerUtils
						.getListOfHandledMonomers(polymer.getPolymerElements().getListOfElements()).get(0);

				if (null == monomer.getCanSMILES() || monomer.getCanSMILES().length() == 0) {
					return true;
				}

				if (monomer.containAnyAtom()) {
					return true;
				}
			}
		}

		return false;

	}

	/**
	 * @param smiles
	 *            given smiles
	 * @return unique extended smiles
	 */
	public static String getUniqueExtendedSMILES(String smiles) {
		return smiles;
	}

	/**
	 * method to generate canonical smiles for one single PolymerNotation
	 *
	 * @param polymer
	 *            PolymerNotation
	 * @return smiles for the sinlge given PolymerNotation
	 * @throws BuilderMoleculeException
	 *             if the molecule can't be built
	 * @throws HELM2HandledException
	 *             if it contains HELM2 specific features, so that it can not be
	 *             casted to HELM1 Format
	 * @throws CTKSmilesException
	 *             if it contains an invalid smiles
	 * @throws CTKException
	 *             general ChemToolKit exception passed to HELMToolKit
	 * @throws NotationException
	 *             if notation is not valid
	 * @throws ChemistryException
	 *             if the Chemistry Engine can not be initialized
	 */
	public static String getCanonicalSMILESForPolymer(PolymerNotation polymer) throws BuilderMoleculeException,
			HELM2HandledException, CTKSmilesException, CTKException, NotationException, ChemistryException {
		AbstractMolecule molecule = BuilderMolecule.buildMoleculefromSinglePolymer(polymer).getMolecule();
		molecule = BuilderMolecule.mergeRgroups(molecule);

		return Chemistry.getInstance().getManipulator().canonicalize(Chemistry.getInstance().getManipulator()
				.convertMolecule(molecule, AbstractChemistryManipulator.StType.SMILES));
	}

	/**
	 * method to generate smiles for one single PolymerNotation
	 *
	 * @param polymer
	 *            PolymerNotation
	 * @return smiles for the sinlge given PolymerNotation
	 * @throws BuilderMoleculeException
	 *             if the molecule can't be built
	 * @throws HELM2HandledException
	 *             if it contains HELM2 specific features, so that it can not be
	 *             casted to HELM1 Format
	 * @throws CTKSmilesException
	 *             if it contains an invalid smiles
	 * @throws CTKException
	 *             general ChemToolKit exception passed to HELMToolKit
	 * @throws NotationException
	 *             if notation is not valid
	 * @throws ChemistryException
	 *             if the Chemistry Engine can not be initialized
	 */
	public static String getSMILESforPolymer(PolymerNotation polymer) throws BuilderMoleculeException,
			HELM2HandledException, CTKSmilesException, CTKException, NotationException, ChemistryException {
		AbstractMolecule molecule = BuilderMolecule.buildMoleculefromSinglePolymer(polymer).getMolecule();
		molecule = BuilderMolecule.mergeRgroups(molecule);

		return Chemistry.getInstance().getManipulator().convertMolecule(molecule,
				AbstractChemistryManipulator.StType.SMILES);
	}

	/**
	 * Converts molfile with the given attachments in smiles with atom mapping
	 * 
	 * @param molfile
	 *            given molfile
	 * @param attachments
	 *            given attachments of the molfile
	 * @return smiles with atom mapping
	 * @throws CTKException
	 *             if the molfile can not be converted to smiles
	 * @throws ChemistryException
	 *             if the Chemistry Engine can not be initialized
	 */
	public static String convertMolToSMILESWithAtomMapping(String molfile, List<Attachment> attachments)
			throws CTKException, ChemistryException {

		String smiles = Chemistry.getInstance().getManipulator().convertMolIntoSmilesWithAtomMapping(molfile);

		for (Attachment attachment : attachments) {
			int r = Integer.valueOf(attachment.getLabel().replaceAll("\\D+", ""));
			smiles = smiles.replace("[*:" + r + "]", "[" + attachment.getCapGroupName() + ":" + r + "]");
		}

		return smiles;

	}
	
	/**
	 * returns if structure is connected
	 * 
	 * @param molfile
	 * @return boolean if structure is connected
	 * @throws CTKException
	 * @throws ChemistryException
	 */
	public static boolean isConnected(String molfile) throws CTKException, ChemistryException {
		return Chemistry.getInstance().getManipulator().isConnected(molfile);
	}
}
