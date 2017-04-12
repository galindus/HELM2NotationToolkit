/**
 * *****************************************************************************
 * Copyright C 2017, The Pistoia Alliance
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
package org.helm.notation2;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AttachmentSaverTest {

	
	/**
	 * method to save the default attachment DB
	 * @throws Exception
	 */
	public void saveAttachmentDB() throws Exception {

		Map<String, Attachment> attachments =  initAttachments();
		ObjectMapper mapper = new ObjectMapper();

		mapper.writeValue(new File("src/main/resources/org/helm/notation2/resources"
				+ File.separator + "Attachments.json"), attachments);

	
	}
	
	
	
	 private static Map<String, Attachment> initAttachments() {
		    Map<String, Attachment> attachments = new TreeMap<String, Attachment>(String.CASE_INSENSITIVE_ORDER);

		    Attachment att = new Attachment();
		    att = new Attachment();
		    att.setAlternateId("R1-H");
		    att.setLabel("R1");
		    att.setCapGroupName("H");
		    att.setCapGroupSMILES("[*:1][H]");
		    attachments.put(att.getAlternateId(), att);
		    
		    att = new Attachment();
		    att.setAlternateId("R2-H");
		    att.setLabel("R2");
		    att.setCapGroupName("H");
		    att.setCapGroupSMILES("[*:2][H]");
		    attachments.put(att.getAlternateId(), att);

		    att = new Attachment();
		    att.setAlternateId("R3-H");
		    att.setLabel("R3");
		    att.setCapGroupName("H");
		    att.setCapGroupSMILES("[*:3][H]");
		    attachments.put(att.getAlternateId(), att);
		    
		    att = new Attachment();
		    att.setAlternateId("R4-H");
		    att.setLabel("R4");
		    att.setCapGroupName("H");
		    att.setCapGroupSMILES("[*:4][H]");
		    attachments.put(att.getAlternateId(), att);
		    
		    att = new Attachment();
		    att.setAlternateId("R5-H");
		    att.setLabel("R5");
		    att.setCapGroupName("H");
		    att.setCapGroupSMILES("[*:5][H]");
		    attachments.put(att.getAlternateId(), att);
		    
		    att = new Attachment();
		    att.setAlternateId("R1-OH");
		    att.setLabel("R1");
		    att.setCapGroupName("OH");
		    att.setCapGroupSMILES("O[*:1]");
		    attachments.put(att.getAlternateId(), att);

		    att = new Attachment();
		    att.setAlternateId("R2-OH");
		    att.setLabel("R2");
		    att.setCapGroupName("OH");
		    att.setCapGroupSMILES("O[*:2]");
		    attachments.put(att.getAlternateId(), att);

		    att = new Attachment();
		    att.setAlternateId("R3-OH");
		    att.setLabel("R3");
		    att.setCapGroupName("OH");
		    att.setCapGroupSMILES("O[*:3]");
		    attachments.put(att.getAlternateId(), att);

		    att = new Attachment();
		    att.setAlternateId("R4-OH");
		    att.setLabel("R4");
		    att.setCapGroupName("OH");
		    att.setCapGroupSMILES("O[*:4]");
		    attachments.put(att.getAlternateId(), att);
		    
		    att = new Attachment();
		    att.setAlternateId("R5-OH");
		    att.setLabel("R5");
		    att.setCapGroupName("OH");
		    att.setCapGroupSMILES("O[*:5]");
		    attachments.put(att.getAlternateId(), att);
		    
		    return attachments;
		  }
}
