/*******************************************************************************
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
 ******************************************************************************/
package org.helm.notation2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * loads attachments from a given input stream
 * 
 * @author hecht
 *
 */
public class AttachmentLoader {

	private static final Logger LOG = LoggerFactory.getLogger(AttachmentLoader.class);

	/**
	 * default private constructor
	 */
	private AttachmentLoader() {

	}

	/**
	 * reads the attachments from the given inputstream and returns the
	 * attachment db
	 * 
	 * @param in
	 *            inputstream the given file containing all attachments
	 * @return attachment db
	 * @throws IOException
	 *             if the attachments in the given file can not be loaded or an
	 *             attachment is not valid
	 */
	public static Map<String, Attachment> loadAttachments(InputStream in) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<TreeMap<String, Attachment>> typeRef = new TypeReference<TreeMap<String, Attachment>>() {
		};
		TreeMap<String, Attachment> attachments;

		try {
			attachments = mapper.readValue(in, typeRef);
			LOG.info("Attachments could be loaded");

			for (Map.Entry<String, Attachment> entry : attachments.entrySet()) {
				Attachment currentAttachment = entry.getValue();

				if (!validateAttachment(currentAttachment)) {
					throw new IOException("Attachment is not valid: " + currentAttachment.getAlternateId());
				}
			}
			return attachments;
		} catch (IOException e) {

			throw new IOException("Attachments in the given file can not be loaded.");

		}
	}

	/**
	 * validates the current attachment
	 * @param currentAttachment given attachment
	 * @return true, if the current attachment is valid, false otherwisesr
	 */
	public static boolean validateAttachment(Attachment currentAttachment) {
		try {
			currentAttachment.getId();

			if (currentAttachment.getAlternateId() == "null" || currentAttachment.getAlternateId().isEmpty()) {
				return false;
			}
			if (currentAttachment.getCapGroupName() == "null" || currentAttachment.getCapGroupName().isEmpty()) {
				return false;
			}
			if (currentAttachment.getCapGroupSMILES() == "null" || currentAttachment.getCapGroupSMILES().isEmpty()) {
				return false;
			}
			if (currentAttachment.getLabel().equals("null") || currentAttachment.getLabel().equals(" ")) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
