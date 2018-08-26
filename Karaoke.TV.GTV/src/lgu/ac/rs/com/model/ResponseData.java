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
 * 2016 All rights (c)KYGroup Co.,Ltd. reserved.
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	.prj
 * filename	:	ResponseData.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv.api.gtv.lgu
 *    |_ ResponseData.java
 * </pre>
 */
package lgu.ac.rs.com.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;

/**
 * <pre>
 *
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-08-05
 */
public class ResponseData implements Serializable {
	private static final long serivalVersionUID = 20080204L;
	public String return_code = "";
	public String return_message = "";
	public Hashtable fields = new Hashtable();

	public ResponseData() {
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		Hashtable hash = new Hashtable();
		hash.put("return_code", this.return_code);
		hash.put("return_message", this.return_message);
		hash.put("fields", this.fields);
		oos.writeObject(hash);
	}

	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		Hashtable hash = (Hashtable) ois.readObject();
		this.return_code = (String) hash.get("return_code");
		this.return_message = (String) hash.get("return_message");
		this.fields = (Hashtable) hash.get("fields");
	}

	public long getSerivalVersionUID() {
		return 20080204L;
	}
}
