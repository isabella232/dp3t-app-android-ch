/*
 * Copyright (c) 2020 Ubique Innovation AG <https://www.ubique.ch>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */
package ch.admin.bag.dp3t.networking.models;

public class InfoBoxModel {

	private String title;
	private String msg;
	private String url;
	private String urlTitle;
	private Boolean isDismissible;
	private String infoId;

	public InfoBoxModel(String title, String msg, String url, String urlTitle, Boolean isDismissible, String infoId) {
		this.title = title;
		this.msg = msg;
		this.url = url;
		this.urlTitle = urlTitle;
		this.isDismissible = isDismissible;
		this.infoId = infoId;
	}

	public String getTitle() {
		return title;
	}

	public String getMsg() {
		return msg;
	}

	public String getUrl() {
		return url;
	}

	public String getUrlTitle() {
		return urlTitle;
	}

	public Boolean getDismissible() { return isDismissible; }

	public String getInfoId() { return infoId; }

}
