/*
 * Copyright 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 2015 All rights (c)KYmedia Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.UTIL
 * filename	:	HtmlBubble.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.util
 *    |_ HtmlBubble.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.app.html;

import org.jsoup.nodes.Document;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author	isyoon
 * @since	2015. 6. 29.
 * @version 1.0
 */
/**
 * 
 * <pre>
 * TODO
 * html미리보기정보
 * </pre>
 * 
 * @author isyoon
 * 
 */
public class HtmlBubble {
	final static int MAX_LEN_DESCRIPTION = 64;
	public Document doc;
	public String body;
	public String title = "";
	public String domain = "";
	public String image = "";
	public String description = "";

	public HtmlBubble(Document doc, String body, String title, String domain, String image,
			String description) {
		super();
		this.doc = doc;
		this.body = body.replace("\"", "'");
		this.title = title.replace("\"", "'");
		this.domain = domain;
		this.image = image;
		this.description = description.replace("\"", "'");
	}

	public HtmlBubble() {
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 * 
	 * @param doc
	 *          the doc to set
	 */
	public void setDoc(Document doc) {
		this.doc = doc;
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 * 
	 * @param body
	 *          the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 * 
	 * @param title
	 *          the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 * 
	 * @param domain
	 *          the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 * 
	 * @param image
	 *          the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 * 
	 * @param description
	 *          the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
