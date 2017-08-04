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
 * project	:	Karaoke.UTIL.XML
 * filename	:	XMLUtil.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.util.xml
 *    |_ XMLUtil.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.app.html;

import kr.kymedia.karaoke.util.TextUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.util.Log;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author isyoon
 * @since 2015. 6. 29.
 * @version 1.0
 */
public class HtmlUtil {

	/**
	 * 
	 * <pre>
	 * TODO
	 * </pre>
	 * 
	 * <a href="http://ogp.me/">The Open Graph protocol</a>
	 * 
	 * @param vg
	 * @param source
	 * @param url
	 * @param type
	 *          Content-Type확인
	 * @return
	 */
	public static HtmlBubble parseHtml(String source, String url, String type) {

		HtmlBubble html = new HtmlBubble();

		try {
			if (TextUtil.isEmpty(type) && TextUtil.isEmpty(source)) {
				Log.e("[parseHtml][NG:" + type + "]", source);
				return html;
			}

			boolean isHtml = true;
			boolean isImage = false;

			if (!TextUtil.isEmpty(type)) {
				isHtml = type.toLowerCase().trim().contains("text");
				isImage = type.toLowerCase().trim().contains("image");
			}

			if (isImage) {
				isHtml = false;
			}

			Document doc = null;

			if (isHtml) {
				doc = Jsoup.parse(source);
			}

			if (isHtml && doc == null) {
				Log.e("[parseHtml][NG:" + type + "]", source);
				return html;
			}

			// Log.e("[parseHtml]", url);
			// Log.e("[parseHtml]", doc.select("title").text());

			// boolean isExistOGTitle = false;
			// boolean isExistOGDomain = false;
			boolean isExistOGImage = false;
			boolean isExistOGDescription = false;

			String title = "";
			String domain = "";
			String image = "";
			String description = "";

			/**
			 * !!!html파싱시작!!!
			 */
			if (isHtml && doc != null) {
				// The Open Graph protocol
				for (Element p : doc.select("meta")) {
					// Log.e("[parseHtml]", "[meta]" + p);

					if (TextUtil.isEmpty(title)
							&& (p.attr("name").toLowerCase().contains("title") || p.attr("property")
									.toLowerCase().contains("title"))) {
						// title
						title = p.attr("content");
						// isExistOGTitle = true;
					} else if (TextUtil.isEmpty(domain)
							&& (p.attr("name").toLowerCase().contains("domain") || p.attr("property")
									.toLowerCase().contains("domain"))) {
						// domain
						domain = p.attr("content");
						// isExistOGDomain = true;
					} else if (/* TextUtil.isEmpty(description) && */
					(p.attr("name").toLowerCase().contains("description") || p.attr("property").toLowerCase()
							.contains("description"))) {
						// description
						description = p.attr("content");
						isExistOGDescription = true;
					} else if (TextUtil.isEmpty(image)
							&& (p.attr("name").toLowerCase().contains("image") || p.attr("property")
									.toLowerCase().contains("image"))) {
						// image
						image = p.attr("content");
						isExistOGImage = true;
					}
				}

				if (TextUtil.isEmpty(title)) {
					title = doc.select("title").text();
				}

				if (!isExistOGDescription || !isExistOGImage) {
					// !!!고난의시작!!!
					boolean content = false;

					for (Element p : doc.body().getAllElements()) {
						// Log.e("[parseHtml]", "[tags:" + p.tagName() + "]" + p);
						// Log.e("[parseHtml]", "[tags:" + p.tagName() + "]" + p.className());

						// HTML기사내용시작확인
						if (!content) {
							if (p.className().toLowerCase().contains("title")
									|| p.className().toLowerCase().contains("main")
									|| p.className().toLowerCase().contains("article")) {
								// Log.e("[parseHtml]", "!!![" + p.tagName() + ":" + p.className() + "]!!!\n" + p);
								content = true;
							}

							for (Attribute attr : p.attributes()) {
								if (attr.getKey().toLowerCase().contains("title")
										|| attr.getKey().toLowerCase().contains("main")
										|| attr.getKey().toLowerCase().contains("article")) {
									// Log.e("[parseHtml]", "!!![" + p.tagName() + ":" + attr.getKey() + "]!!!\n" + p);
									content = true;
									break;
								}
							}

						}

						// if (content && p.children().size() == 0) {
						if (content) {
							// Log.e("[parseHtml]", "[text]" + p);

							if (!isExistOGDescription && description.length() < 128) {
								// Log.e("[parseHtml]", "!!![text]!!!" + p);
								description += p.text();
							}

							if (!isExistOGImage) {
								if (("img").equalsIgnoreCase(p.tagName()) && TextUtil.isNetworkUrl(p.attr("src"))) {
									// Log.e("[parseHtml]", "!!![image]!!!" + p);
									image = p.attr("src");
									isExistOGImage = true;
									;
								}
							}

						}

						if (description.length() > HtmlBubble.MAX_LEN_DESCRIPTION) {
							description = description.substring(0, HtmlBubble.MAX_LEN_DESCRIPTION);
							isExistOGDescription = true;
						}

						if (isExistOGDescription && isExistOGImage) {
							break;
						}
					}

					if (!content && doc.body().select("p").first() != null) {
						description = doc.body().select("p").first().text();
					}

					if (!content && doc.body().select("span").first() != null) {
						description = doc.body().select("span").first().text();
					}

					if (!content && TextUtil.isEmpty(description)) {
						description = doc.text();
					}

					// Log.e("[parseHtml]", "[body]\n" + description);
				}
			}

			// 그래도이미지가없다.
			if (!TextUtil.isNetworkUrl(image)) {
				if (isImage) {
					image = url;
					title = url;
				} else {
					if (doc != null) {
						Element e = doc.head().select("link[href~=.*\\.ico]").first();
						if (e != null && TextUtil.isNetworkUrl(e.attr("href"))) {
							image = e.attr("href");
						}
					}
				}
			}

			String body = "<a href='" + url + "'><font color='#0066FF'><b>" + title + "</b></font></a>";
			body += "<br>";
			body += "<small>" + domain + "</blockquote>";
			body += "<br>";
			// html += "<img src='" + urlImage + "'></img>";
			body += "<small><blockquote>" + description + "</blockquote></small>";

			// html = new html(doc, body, title, domain, image, description);
			html.setDoc(doc);
			html.setBody(body);
			html.setTitle(title);
			html.setDomain(domain);
			html.setImage(image);
			html.setDescription(description);

			// Log.e("[parseHtml]", "[html:title]" + html.title);
			// Log.e("[parseHtml]", "[html:description]" + html.description);
			// Log.e("[parseHtml]", "[html:image]" + html.image);
			// Log.e("[parseHtml]", "[html:body]" + html.body);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return html;
	}

}
