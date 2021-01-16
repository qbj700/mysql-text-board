package com.sbs.example.mysqlTextBoard.service;

import java.util.List;

import com.sbs.example.mysqlTextBoard.dao.TagDao;
import com.sbs.example.mysqlTextBoard.dto.Tag;

public class TagService {
	private TagDao tagDao;

	public TagService() {
		tagDao = new TagDao();
	}

	public List<Tag> getDedupTagsByRelTypeCode(String relTypeCode) {
		return tagDao.getDedupTagsByRelTypeCode(relTypeCode);
	}

	public List<String> getDedupTagBodiesByRelTypeCode(String relTypeCode) {
		return tagDao.getDedupTagBodiesByRelTypeCode(relTypeCode);
	}

}
