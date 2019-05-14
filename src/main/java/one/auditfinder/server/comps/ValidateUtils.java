package one.auditfinder.server.comps;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.RegexValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import one.auditfinder.server.common.ValidateParam;
import one.auditfinder.server.common.ValidateResult;
import one.auditfinder.server.exception.ApiException;
import one.auditfinder.server.exception.PageException;
import one.auditfinder.server.statics.Strs;
import one.auditfinder.server.statics.Values;


@Component
public class ValidateUtils {

	@Autowired
	private CommonUtils commonUtils;

	@Autowired
	private MsgUtils msgUtils;

	public boolean checkNull(String str) {
		if (str != null && str.length() > 0)
			return true;
		return false;
	}


	public ValidateResult checkNull(Object obj, String[] properties) {
		ValidateResult vr = new ValidateResult();
		if (obj == null || properties == null || properties.length == 0) {
			vr.setResult(Values.ID_CHECK_EXCEPTION);
			vr.setValue(msgUtils.getMsg(Strs.ID_INVALIDPARAM_ERROR));
			return vr;
		}
		try {
			for (int i = 0; i < properties.length; i++) {
				Object iobj = PropertyUtils.getSimpleProperty(obj, properties[i]);
				if (iobj == null) {
					vr.setPropertyIndex(i);
					vr.setResult(Values.ID_CHECK_NULL_ERROR);
					return vr;
				}
				if (iobj instanceof String) {
					if (((String) iobj).length() == 0) {
						vr.setPropertyIndex(i);
						vr.setResult(Values.ID_CHECK_NULL_ERROR);
						return vr;
					}
				}
			}
		} catch (Exception e) {
			vr.setResult(Values.ID_CHECK_EXCEPTION);
			vr.setValue(e.getMessage());
			return vr;
		}
		vr.setResult(Values.ID_CHECK_OK);
		return vr;
	}

	public ValidateResult checkNullC(Object obj, String[] properties) {
		ValidateResult vr = new ValidateResult();
		if (obj == null || properties == null || properties.length == 0) {
			vr.setResult(Values.ID_CHECK_EXCEPTION);
			vr.setValue(msgUtils.getMsg(Strs.ID_INVALIDPARAM_ERROR));
			return vr;
		}
		try {
			for (int i = 0; i < properties.length; i++) {
				Object iobj = PropertyUtils.getProperty(obj, properties[i]);
				if (iobj == null) {
					vr.setPropertyIndex(i);
					vr.setResult(Values.ID_CHECK_NULL_ERROR);
					return vr;
				}
				if (iobj instanceof String) {
					if (((String) iobj).length() == 0) {
						vr.setPropertyIndex(i);
						vr.setResult(Values.ID_CHECK_NULL_ERROR);
						return vr;
					}
				}
			}
		} catch (Exception e) {
			vr.setResult(Values.ID_CHECK_EXCEPTION);
			vr.setValue(e.getMessage());
			return vr;
		}
		vr.setResult(Values.ID_CHECK_OK);
		return vr;
	}

	public ValidateResult checkNull(List<? extends Object> objs, String[] properties) {
		ValidateResult vr = new ValidateResult();
		if (objs == null || objs.size() == 0 || properties == null || properties.length == 0) {
			vr.setResult(Values.ID_CHECK_EXCEPTION);
			vr.setValue(msgUtils.getMsg(Strs.ID_INVALIDPARAM_ERROR));
			return vr;
		}
		try {
			for (Object obj : objs) {
				for (int i = 0; i < properties.length; i++) {
					Object iobj = PropertyUtils.getSimpleProperty(obj, properties[i]);
					if (iobj == null) {
						vr.setPropertyIndex(i);
						vr.setResult(Values.ID_CHECK_NULL_ERROR);
						return vr;
					}
					if (iobj instanceof String) {
						if (((String) iobj).length() == 0) {
							vr.setPropertyIndex(i);
							vr.setResult(Values.ID_CHECK_NULL_ERROR);
							return vr;
						}
					}
				}
			}
		} catch (Exception e) {
			vr.setResult(Values.ID_CHECK_EXCEPTION);
			vr.setValue(e.getMessage());
			return vr;
		}
		vr.setResult(Values.ID_CHECK_OK);
		return vr;
	}

	public ValidateResult checkNullC(List<? extends Object> objs, String[] properties) {
		ValidateResult vr = new ValidateResult();
		if (objs == null || objs.size() == 0 || properties == null || properties.length == 0) {
			vr.setResult(Values.ID_CHECK_EXCEPTION);
			vr.setValue(msgUtils.getMsg(Strs.ID_INVALIDPARAM_ERROR));
			return vr;
		}
		try {
			for (int oidx = 0; oidx < objs.size(); oidx++) {
				Object obj = objs.get(0);
				for (int i = 0; i < properties.length; i++) {
					Object iobj = PropertyUtils.getProperty(obj, properties[i]);
					if (iobj == null) {
						vr.setObjectIndex(oidx);
						vr.setPropertyIndex(i);
						vr.setResult(Values.ID_CHECK_NULL_ERROR);
						return vr;
					}
					if (iobj instanceof String) {
						if (((String) iobj).length() == 0) {
							vr.setObjectIndex(oidx);
							vr.setPropertyIndex(i);
							vr.setResult(Values.ID_CHECK_NULL_ERROR);
							return vr;
						}
					}
				}
			}
		} catch (Exception e) {
			vr.setResult(Values.ID_CHECK_EXCEPTION);
			vr.setValue(e.getMessage());
			return vr;
		}
		vr.setResult(Values.ID_CHECK_OK);
		return vr;
	}

	public void checkNull(Object obj, String[] properties, String[] fields) {
		ValidateResult vr = checkNull(obj, properties);
		int rt = vr.getResult();
		if (rt == Values.ID_CHECK_OK)
			return;
		raiseValidateResultException(vr, fields);
	}

	private void checkValidateAll_(Object obj, String[] properties, ValidateParam[] valiparams,
			List<ValidateResult> vrs, int objIdx, boolean isSimple) throws Exception {
		for (int pix = 0; pix < properties.length; pix++) {
			Object iobj = null;

			if (isSimple)
				iobj = PropertyUtils.getSimpleProperty(obj, properties[pix]);
			else
				iobj = PropertyUtils.getProperty(obj, properties[pix]);

			if (iobj == null) {
				if (valiparams[pix].isNullSkip())
					continue;
				ValidateResult vr = checkNull(obj, properties);
				vr.setObjectIndex(objIdx);
				vr.setPropertyIndex(pix);
				vr.setResult(Values.ID_CHECK_NULL_ERROR);
				vrs.add(vr);
			} else {
				if (iobj instanceof String) {
					String s = (String) iobj;
					if (s.length() == 0) {
						if (valiparams[pix].isNullSkip())
							continue;
						ValidateResult vr = checkNull(obj, properties);
						vr.setObjectIndex(objIdx);
						vr.setPropertyIndex(pix);
						vr.setResult(Values.ID_CHECK_NULL_ERROR);
						vrs.add(vr);
					}
					if (valiparams[pix].isStrMinMax()) {
						int rt = checkLength_(s, (int) valiparams[pix].getMin(), (int) valiparams[pix].getMax());
						if (rt != Values.ID_CHECK_OK) {
							ValidateResult vr = checkNull(obj, properties);
							vr.setObjectIndex(objIdx);
							vr.setPropertyIndex(pix);
							vr.setResult(Values.ID_CHECK_NULL_ERROR);
							vrs.add(vr);
						}
					}
					if (valiparams[pix].isPattern()) {
						Pattern ptn = valiparams[pix].getPattern();
						if (ptn != null) {
							if (!checkRegex_(s, ptn)) {
								ValidateResult vr = checkNull(obj, properties);
								vr.setObjectIndex(objIdx);
								vr.setPropertyIndex(pix);
								vr.setResult(Values.ID_CHECK_REGEX_ERROR);
								vrs.add(vr);
							}
						}
						Pattern[] ptns = valiparams[pix].getPatterns();
						if (ptns != null && ptns.length > 0) {
							if (!checkRegex_(s, ptns)) {
								ValidateResult vr = checkNull(obj, properties);
								vr.setObjectIndex(objIdx);
								vr.setPropertyIndex(pix);
								vr.setResult(Values.ID_CHECK_REGEX_ERROR);
								vrs.add(vr);
							}
						}
					}
					if (valiparams[pix].isRegExStr()) {
						String regex = valiparams[pix].getRegex();
						if (regex != null && regex.length() > 0) {
							if (valiparams[pix].getExKind() == 1) {
								if (!checkRegexI_(s, regex)) {
									ValidateResult vr = checkNull(obj, properties);
									vr.setObjectIndex(objIdx);
									vr.setPropertyIndex(pix);
									vr.setResult(Values.ID_CHECK_REGEX_ERROR);
									vrs.add(vr);
								}
							} else {
								if (!checkRegex_(s, regex)) {
									ValidateResult vr = checkNull(obj, properties);
									vr.setObjectIndex(objIdx);
									vr.setPropertyIndex(pix);
									vr.setResult(Values.ID_CHECK_REGEX_ERROR);
									vrs.add(vr);
								}
							}
						}
						String[] regexs = valiparams[pix].getRegexs();
						if (regexs != null && regexs.length > 0) {
							if (valiparams[pix].getExKind() == 1) {
								if (!checkRegexI_(s, regexs)) {
									ValidateResult vr = checkNull(obj, properties);
									vr.setObjectIndex(objIdx);
									vr.setPropertyIndex(pix);
									vr.setResult(Values.ID_CHECK_REGEX_ERROR);
									vrs.add(vr);
								}
							} else {
								if (!checkRegex_(s, regexs)) {
									ValidateResult vr = checkNull(obj, properties);
									vr.setObjectIndex(objIdx);
									vr.setPropertyIndex(pix);
									vr.setResult(Values.ID_CHECK_REGEX_ERROR);
									vrs.add(vr);
								}
							}
						}
					}
					if (valiparams[pix].isEmail()) {
						if (!checkEmail(s)) {
							ValidateResult vr = checkNull(obj, properties);
							vr.setObjectIndex(objIdx);
							vr.setPropertyIndex(pix);
							vr.setResult(Values.ID_CHECK_EMAIL_ERROR);
							vrs.add(vr);
						}
					}

				} else {
					if (valiparams[pix].isValueMinMax()) {
						long lv = (Long) iobj;
						int rt = checkMinMaxValue(lv, valiparams[pix].getMin(), valiparams[pix].getMax());
						if (rt != Values.ID_CHECK_OK) {
							ValidateResult vr = checkNull(obj, properties);
							vr.setObjectIndex(objIdx);
							vr.setPropertyIndex(pix);
							vr.setResult(rt);
							vrs.add(vr);
						}
					}
				}
			}
		}
	}

	private void checkValidate(Object obj, String[] properties, ValidateParam[] valiparams, String[] fields,
			ValidateResult vr, boolean isSimple) throws Exception {
		for (int pix = 0; pix < properties.length; pix++) {
			vr.setPropertyIndex(pix);
			Object iobj = null;

			if (isSimple)
				iobj = PropertyUtils.getSimpleProperty(obj, properties[pix]);
			else
				iobj = PropertyUtils.getProperty(obj, properties[pix]);

			if (iobj == null) {
				if(valiparams[pix].isNullSkip() ) continue;
				vr.setResult(Values.ID_CHECK_NULL_ERROR);
				return;
			} else {
				if (iobj instanceof String) {
					String s = (String) iobj;
					if (s.length() == 0) {
						if(valiparams[pix].isNullSkip() ) continue;
						vr.setResult(Values.ID_CHECK_NULL_ERROR);
						return;
					}
					if (valiparams[pix].isStrMinMax()) {
						int rt = checkLength_(s, (int) valiparams[pix].getMin(), (int) valiparams[pix].getMax());
						if (rt != Values.ID_CHECK_OK) {
							vr.setResult(rt);
							return;
						}
					}
					if (valiparams[pix].isPattern()) {
						Pattern ptn = valiparams[pix].getPattern();
						if (ptn != null) {
							if (!checkRegex_(s, ptn)) {
								vr.setResult(Values.ID_CHECK_REGEX_ERROR);
								return;
							}
						}
						Pattern[] ptns = valiparams[pix].getPatterns();
						if (ptns != null && ptns.length > 0) {
							if (!checkRegex_(s, ptns)) {
								vr.setResult(Values.ID_CHECK_REGEX_ERROR);
								return;
							}
						}
					}
					if (valiparams[pix].isRegExStr()) {
						String regex = valiparams[pix].getRegex();
						if (regex != null && regex.length() > 0) {
							if (valiparams[pix].getExKind() == 1) {
								if (!checkRegexI_(s, regex)) {
									vr.setResult(Values.ID_CHECK_REGEX_ERROR);
									return;
								}
							} else {
								if (!checkRegex_(s, regex)) {
									vr.setResult(Values.ID_CHECK_REGEX_ERROR);
									return;
								}
							}
						}
						String[] regexs = valiparams[pix].getRegexs();
						if (regexs != null && regexs.length > 0) {
							if (valiparams[pix].getExKind() == 1) {
								if (!checkRegexI_(s, regexs)) {
									vr.setResult(Values.ID_CHECK_REGEX_ERROR);
									return;
								}
							} else {
								if (!checkRegex_(s, regexs)) {
									vr.setResult(Values.ID_CHECK_REGEX_ERROR);
									return;
								}
							}
						}
					}
					if (valiparams[pix].isEmail()) {
						if (!checkEmail(s)) {
							vr.setResult(Values.ID_CHECK_EMAIL_ERROR);
							return;
						}
					}

				} else {
					if (valiparams[pix].isValueMinMax()) {
						long lv = (Long) iobj;
						int rt = checkMinMaxValue(lv, valiparams[pix].getMin(), valiparams[pix].getMax());
						if (rt != Values.ID_CHECK_OK) {
							vr.setResult(rt);
							return;
						}
					}
				}
			}
		}
		vr.setResult(Values.ID_CHECK_OK);
	}

	public List<ValidateResult> checkValidateAll(List<? extends Object> objs, String[] properties,
			ValidateParam[] valiparams, String[] fields) {
		if (objs == null || objs.size() == 0 || properties == null || valiparams == null
				|| properties.length != valiparams.length) {
			ValidateResult vr = new ValidateResult();
			vr.setResult(Values.ID_CHECK_EXCEPTION);
			vr.setValue(msgUtils.getMsg(Strs.ID_INVALIDPARAM_ERROR));
			raiseValidateResultException(vr, null);
		}
		List<ValidateResult> vrs = new ArrayList<ValidateResult>();
		try {
			for (int oix = 0; oix < objs.size(); oix++) {
				Object obj = objs.get(oix);
				if (obj == null)
					continue;
				checkValidateAll_(obj, properties, valiparams, vrs, oix, true);
			}
		} catch (Exception e) {
			ValidateResult vr = new ValidateResult();
			vr.setResult(Values.ID_CHECK_EXCEPTION);
			vr.setValue(e.getMessage());
			vrs.add(vr);
		}
		if (vrs.size() == 0)
			return null;
		return vrs;
	}

	public List<ValidateResult> checkValidateAllC(List<? extends Object> objs, String[] properties,
			ValidateParam[] valiparams, String[] fields) {
		if (objs == null || objs.size() == 0 || properties == null || valiparams == null
				|| properties.length != valiparams.length) {
			ValidateResult vr = new ValidateResult();
			vr.setResult(Values.ID_CHECK_EXCEPTION);
			vr.setValue(msgUtils.getMsg(Strs.ID_INVALIDPARAM_ERROR));
			raiseValidateResultException(vr, null);
		}
		List<ValidateResult> vrs = new ArrayList<ValidateResult>();
		try {
			for (int oix = 0; oix < objs.size(); oix++) {
				Object obj = objs.get(oix);
				if (obj == null)
					continue;
				checkValidateAll_(obj, properties, valiparams, vrs, oix, false);
			}
		} catch (Exception e) {
			ValidateResult vr = new ValidateResult();
			vr.setResult(Values.ID_CHECK_EXCEPTION);
			vr.setValue(e.getMessage());
			vrs.add(vr);
		}
		if (vrs.size() == 0)
			return null;
		return vrs;
	}

	public List<ValidateResult> checkValidateAll(Object obj, String[] properties, ValidateParam[] valiparams,
			String[] fields) {
		if (obj == null || properties == null || valiparams == null || properties.length != valiparams.length) {
			ValidateResult vr = new ValidateResult();
			vr.setResult(Values.ID_CHECK_EXCEPTION);
			vr.setValue(msgUtils.getMsg(Strs.ID_INVALIDPARAM_ERROR));
			raiseValidateResultException(vr, null);
		}
		List<ValidateResult> vrs = new ArrayList<ValidateResult>();
		try {
			checkValidateAll_(obj, properties, valiparams, vrs, 0, true);
		} catch (Exception e) {
			ValidateResult vr = new ValidateResult();
			vr.setResult(Values.ID_CHECK_EXCEPTION);
			vr.setValue(e.getMessage());
			vrs.add(vr);
		}

		if (vrs.size() == 0)
			return null;
		return vrs;
	}

	public List<ValidateResult> checkValidateAllC(Object obj, String[] properties, ValidateParam[] valiparams,
			String[] fields) {
		if (obj == null || properties == null || valiparams == null || properties.length != valiparams.length) {
			ValidateResult vr = new ValidateResult();
			vr.setResult(Values.ID_CHECK_EXCEPTION);
			vr.setValue(msgUtils.getMsg(Strs.ID_INVALIDPARAM_ERROR));
			raiseValidateResultException(vr, null);
		}
		List<ValidateResult> vrs = new ArrayList<ValidateResult>();
		try {
			checkValidateAll_(obj, properties, valiparams, vrs, 0, false);
		} catch (Exception e) {
			ValidateResult vr = new ValidateResult();
			vr.setResult(Values.ID_CHECK_EXCEPTION);
			vr.setValue(e.getMessage());
			vrs.add(vr);
		}
		if (vrs.size() == 0)
			return null;
		return vrs;
	}

	public void checkValidate(Object obj, String[] properties, ValidateParam[] valiparams, String[] fields) {
		ValidateResult vr = new ValidateResult();
		if (obj == null || properties == null || valiparams == null || properties.length != valiparams.length) {
			vr.setResult(Values.ID_CHECK_EXCEPTION);
			vr.setValue(msgUtils.getMsg(Strs.ID_INVALIDPARAM_ERROR));
			raiseValidateResultException(vr, null);
		}
		try {
			checkValidate(obj, properties, valiparams, fields, vr, true);
			if (vr.getResult() == Values.ID_CHECK_OK)
				return;
			raiseValidateResultException(vr, fields);
		} catch (Exception e) {
			vr.setResult(Values.ID_CHECK_EXCEPTION);
			vr.setValue(e.getMessage());
			raiseValidateResultException(vr, fields);
		}
	}

	public void checkValidate(List<? extends Object> objs, String[] properties, ValidateParam[] valiparams,
			String[] fields) {
		ValidateResult vr = new ValidateResult();
		if (objs == null || objs.size() == 0 || properties == null || valiparams == null
				|| properties.length != valiparams.length) {
			vr.setResult(Values.ID_CHECK_EXCEPTION);
			vr.setValue(msgUtils.getMsg(Strs.ID_INVALIDPARAM_ERROR));
			raiseValidateResultException(vr, null);
		}
		try {
			for (int oix = 0; oix < objs.size(); oix++) {
				Object obj = objs.get(oix);
				if (obj == null)
					continue;
				vr.setObjectIndex(oix);
				checkValidate(obj, properties, valiparams, fields, vr, true);
				if (vr.getResult() != Values.ID_CHECK_OK)
					raiseValidateResultException(vr, fields);
			}
		} catch (Exception e) {
			vr.setResult(Values.ID_CHECK_EXCEPTION);
			vr.setValue(e.getMessage());
			raiseValidateResultException(vr, fields);
		}
	}

	public void checkValidateC(Object obj, String[] properties, ValidateParam[] valiparams, String[] fields) {
		ValidateResult vr = new ValidateResult();
		if (obj == null || properties == null || valiparams == null || properties.length != valiparams.length) {
			vr.setResult(Values.ID_CHECK_EXCEPTION);
			vr.setValue(msgUtils.getMsg(Strs.ID_INVALIDPARAM_ERROR));
			raiseValidateResultException(vr, null);
		}
		try {
			checkValidate(obj, properties, valiparams, fields, vr, false);
			if (vr.getResult() == Values.ID_CHECK_OK)
				return;
			raiseValidateResultException(vr, fields);
		} catch (Exception e) {
			vr.setResult(Values.ID_CHECK_EXCEPTION);
			vr.setValue(e.getMessage());
			raiseValidateResultException(vr, fields);
		}

	}

	public void checkValidateC(List<? extends Object> objs, String[] properties, ValidateParam[] valiparams,
			String[] fields) {
		ValidateResult vr = new ValidateResult();
		if (objs == null || objs.size() == 0 || properties == null || valiparams == null
				|| properties.length != valiparams.length) {
			vr.setResult(Values.ID_CHECK_EXCEPTION);
			vr.setValue(msgUtils.getMsg(Strs.ID_INVALIDPARAM_ERROR));
			raiseValidateResultException(vr, null);
		}
		try {
			for (int oix = 0; oix < objs.size(); oix++) {
				Object obj = objs.get(oix);
				if (obj == null)
					continue;
				vr.setObjectIndex(oix);
				checkValidate(obj, properties, valiparams, fields, vr, false);
				if (vr.getResult() != Values.ID_CHECK_OK)
					raiseValidateResultException(vr, fields);
			}
		} catch (Exception e) {
			vr.setResult(Values.ID_CHECK_EXCEPTION);
			vr.setValue(e.getMessage());
			raiseValidateResultException(vr, fields);
		}
	}

	public void raiseValidateResultException(ValidateResult vr, String[] fields) {
		int result = vr.getResult();
		String objidx = "";
		String fieldname = "";
		if (vr.getObjectIndex() > 0)
			objidx = new StringBuilder().append('[').append(vr.getObjectIndex()).append(']').toString();
		if (fields != null && fields.length > vr.getPropertyIndex() && fields[vr.getPropertyIndex()] != null)
			fieldname = fields[vr.getPropertyIndex()];
		if (result == Values.ID_CHECK_EXCEPTION) {
			String s = new StringBuilder().append(vr.getValue()).append(' ').append(objidx).append(fieldname)
					.toString();
			if (commonUtils.requestUri().startsWith("/api/"))
				throw new ApiException(msgUtils.getMsg(Strs.ID_EXCETPION_ERROR, s));
			else
				throw new PageException(msgUtils.getMsg(Strs.ID_EXCETPION_ERROR, s));
		}

		if (result == Values.ID_CHECK_NULL_ERROR) {
			if (commonUtils.requestUri().startsWith("/api/"))
				throw new ApiException(msgUtils.getMsg(Strs.ID_VALID_CHECK_NULLMSG, fieldname, objidx));
			else
				throw new PageException(msgUtils.getMsg(Strs.ID_VALID_CHECK_NULLMSG, fieldname, objidx));
		}
		if (result == Values.ID_CHECK_MINLEN_ERROR) {
			if (commonUtils.requestUri().startsWith("/api/"))
				throw new ApiException(
						msgUtils.getMsg(Strs.ID_VALID_CHECK_MINLENGTHMSG, fieldname, vr.getValue(), objidx));
			else
				throw new PageException(
						msgUtils.getMsg(Strs.ID_VALID_CHECK_MINLENGTHMSG, fieldname, vr.getValue(), objidx));
		}
		if (result == Values.ID_CHECK_MAXLEN_ERROR) {
			if (commonUtils.requestUri().startsWith("/api/"))
				throw new ApiException(
						msgUtils.getMsg(Strs.ID_VALID_CHECK_MAXLENGTHMSG, fieldname, vr.getValue(), objidx));
			else
				throw new PageException(
						msgUtils.getMsg(Strs.ID_VALID_CHECK_MAXLENGTHMSG, fieldname, vr.getValue(), objidx));
		}
		if (result == Values.ID_CHECK_MINVAL_ERROR) {
			if (commonUtils.requestUri().startsWith("/api/"))
				throw new ApiException(
						msgUtils.getMsg(Strs.ID_VALID_CHECK_MINVALUEMSG, fieldname, vr.getValue(), objidx));
			else
				throw new PageException(
						msgUtils.getMsg(Strs.ID_VALID_CHECK_MINVALUEMSG, fieldname, vr.getValue(), objidx));
		}
		if (result == Values.ID_CHECK_MAXVAL_ERROR) {
			if (commonUtils.requestUri().startsWith("/api/"))
				throw new ApiException(
						msgUtils.getMsg(Strs.ID_VALID_CHECK_MAXVALUEMSG, fieldname, vr.getValue(), objidx));
			else
				throw new PageException(
						msgUtils.getMsg(Strs.ID_VALID_CHECK_MAXVALUEMSG, fieldname, vr.getValue(), objidx));
		}
		if (result == Values.ID_CHECK_NOTMATCHVAL_ERROR) {
			if (commonUtils.requestUri().startsWith("/api/"))
				throw new ApiException(msgUtils.getMsg(Strs.ID_VALID_CHECK_NOTMATCHVALMSG, fieldname, objidx));
			else
				throw new PageException(msgUtils.getMsg(Strs.ID_VALID_CHECK_NOTMATCHVALMSG, fieldname, objidx));
		}
		if (result == Values.ID_CHECK_REGEX_ERROR) {
			if (commonUtils.requestUri().startsWith("/api/"))
				throw new ApiException(msgUtils.getMsg(Strs.ID_VALID_CHECK_REGEXMSG, fieldname, objidx));
			else
				throw new PageException(msgUtils.getMsg(Strs.ID_VALID_CHECK_REGEXMSG, fieldname, objidx));
		}
		if (result == Values.ID_CHECK_EMAIL_ERROR) {
			if (commonUtils.requestUri().startsWith("/api/"))
				throw new ApiException(msgUtils.getMsg(Strs.ID_VALID_CHECK_EMAILMSG, fieldname, objidx));
			else
				throw new PageException(msgUtils.getMsg(Strs.ID_VALID_CHECK_EMAILMSG, fieldname, objidx));
		}
	}

	public void checkNull(String str, String field) {
		if (checkNull(str))
			return;
		if (commonUtils.requestUri().startsWith("/api/")) {
			if (checkNull(field))
				throw new ApiException(msgUtils.getMsg(Strs.ID_VALID_CHECK_NULLMSG, field, ""));
			else
				throw new ApiException(msgUtils.getMsg(Strs.ID_VALID_CHECK_NULLMSG, "", ""));
		} else {
			if (checkNull(field))
				throw new PageException(msgUtils.getMsg(Strs.ID_VALID_CHECK_NULLMSG, field, ""));
			else
				throw new PageException(msgUtils.getMsg(Strs.ID_VALID_CHECK_NULLMSG, "", ""));
		}
	}

	private int checkLength_(String str, int min, int max) {
		if (min > 0 && min > str.length())
			return Values.ID_CHECK_MINLEN_ERROR;
		if (max > 0 && max < str.length())
			return Values.ID_CHECK_MAXLEN_ERROR;
		return Values.ID_CHECK_OK;
	}

	public int checkLength(String str, int min, int max) {
		if (!checkNull(str))
			return Values.ID_CHECK_NULL_ERROR;
		return checkLength_(str, min, max);
	}

	public void checkLength(String str, int min, int max, String field) {
		int result = checkLength(str, min, max);
		if (result == Values.ID_CHECK_OK)
			return;
		if (commonUtils.requestUri().startsWith("/api/")) {
			if (result == Values.ID_CHECK_NULL_ERROR) {
				if (checkNull(field))
					throw new ApiException(msgUtils.getMsg(Strs.ID_VALID_CHECK_NULLMSG, field, ""));
				else
					throw new ApiException(msgUtils.getMsg(Strs.ID_VALID_CHECK_NULLMSG, "", ""));
			}
			if (result == Values.ID_CHECK_MINLEN_ERROR) {
				if (checkNull(field))
					throw new ApiException(msgUtils.getMsg(Strs.ID_VALID_CHECK_MINLENGTHMSG, field, min));
				else
					throw new ApiException(msgUtils.getMsg(Strs.ID_VALID_CHECK_MINLENGTHMSG, "", min));
			}
			if (result == Values.ID_CHECK_MAXLEN_ERROR) {
				if (checkNull(field))
					throw new ApiException(msgUtils.getMsg(Strs.ID_VALID_CHECK_MAXLENGTHMSG, field, max));
				else
					throw new ApiException(msgUtils.getMsg(Strs.ID_VALID_CHECK_MAXLENGTHMSG, "", max));
			}

		} else {
			if (result == Values.ID_CHECK_NULL_ERROR) {
				if (checkNull(field))
					throw new PageException(msgUtils.getMsg(Strs.ID_VALID_CHECK_NULLMSG, field));
				else
					throw new PageException(msgUtils.getMsg(Strs.ID_VALID_CHECK_NULLMSG, ""));
			}
			if (result == Values.ID_CHECK_MINLEN_ERROR) {
				if (checkNull(field))
					throw new PageException(msgUtils.getMsg(Strs.ID_VALID_CHECK_MINLENGTHMSG, field, min));
				else
					throw new PageException(msgUtils.getMsg(Strs.ID_VALID_CHECK_MINLENGTHMSG, "", min));
			}
			if (result == Values.ID_CHECK_MAXLEN_ERROR) {
				if (checkNull(field))
					throw new PageException(msgUtils.getMsg(Strs.ID_VALID_CHECK_MAXLENGTHMSG, field, max));
				else
					throw new PageException(msgUtils.getMsg(Strs.ID_VALID_CHECK_MAXLENGTHMSG, "", max));
			}
		}
	}

	public int checkMinMaxValue(long val, long min, long max) {
		if (min == max) {
			if (val == min)
				return Values.ID_CHECK_OK;
			return Values.ID_CHECK_NOTMATCHVAL_ERROR;
		}
		if ( min > val)
			return Values.ID_CHECK_MINVAL_ERROR;
		if (max > 0 && max < val)
			return Values.ID_CHECK_MAXVAL_ERROR;
		return Values.ID_CHECK_OK;
	}

	public void checkMinMaxValue(long val, long min, long max, String field) {
		int result = checkMinMaxValue(val, min, max);
		if (result == Values.ID_CHECK_OK)
			return;
		if (commonUtils.requestUri().startsWith("/api/")) {
			if (result == Values.ID_CHECK_MINVAL_ERROR) {
				if (checkNull(field))
					throw new ApiException(msgUtils.getMsg(Strs.ID_VALID_CHECK_MINVALUEMSG, field));
				else
					throw new ApiException(msgUtils.getMsg(Strs.ID_VALID_CHECK_MINVALUEMSG, ""));
			}
			if (result == Values.ID_CHECK_MAXVAL_ERROR) {
				if (checkNull(field))
					throw new ApiException(msgUtils.getMsg(Strs.ID_VALID_CHECK_MAXVALUEMSG, field, min));
				else
					throw new ApiException(msgUtils.getMsg(Strs.ID_VALID_CHECK_MAXVALUEMSG, "", min));
			}
			if (result == Values.ID_CHECK_NOTMATCHVAL_ERROR) {
				if (checkNull(field))
					throw new ApiException(msgUtils.getMsg(Strs.ID_VALID_CHECK_NOTMATCHVALMSG, field, max));
				else
					throw new ApiException(msgUtils.getMsg(Strs.ID_VALID_CHECK_NOTMATCHVALMSG, "", max));
			}

		} else {
			if (result == Values.ID_CHECK_MINVAL_ERROR) {
				if (checkNull(field))
					throw new PageException(msgUtils.getMsg(Strs.ID_VALID_CHECK_MINVALUEMSG, field));
				else
					throw new PageException(msgUtils.getMsg(Strs.ID_VALID_CHECK_MINVALUEMSG, ""));
			}
			if (result == Values.ID_CHECK_MAXVAL_ERROR) {
				if (checkNull(field))
					throw new PageException(msgUtils.getMsg(Strs.ID_VALID_CHECK_MAXVALUEMSG, field, min));
				else
					throw new PageException(msgUtils.getMsg(Strs.ID_VALID_CHECK_MAXVALUEMSG, "", min));
			}
			if (result == Values.ID_CHECK_NOTMATCHVAL_ERROR) {
				if (checkNull(field))
					throw new PageException(msgUtils.getMsg(Strs.ID_VALID_CHECK_NOTMATCHVALMSG, field, max));
				else
					throw new PageException(msgUtils.getMsg(Strs.ID_VALID_CHECK_NOTMATCHVALMSG, "", max));
			}
		}
	}

	public boolean checkEmail(String email) {
		return EmailValidator.getInstance().isValid(email);
	}

	public void checkEmailEx(String email) {
		if (checkEmail(email))
			return;
		if (commonUtils.requestUri().startsWith("/api/"))
			throw new ApiException(msgUtils.getMsg(Strs.ID_VALID_CHECK_EMAILMSG, email));
		else
			throw new PageException(msgUtils.getMsg(Strs.ID_VALID_CHECK_EMAILMSG, email));
	}

	private boolean checkRegex_(String str, Pattern pattern) {
		Matcher m = pattern.matcher(str);
		return m.matches();
	}

	private boolean checkRegex_(String str, Pattern[] patterns) {
		for (int i = 0; i < patterns.length; i++) {
			if (patterns[i].matcher(str).matches())
				return true;
		}
		return false;
	}

	private boolean checkRegex_(String str, String regex) {
		RegexValidator rv = new RegexValidator(regex);
		return rv.isValid(str);
	}

	private boolean checkRegex_(String str, String[] regexs) {
		for (int i = 0; i < regexs.length; i++) {
			RegexValidator rv = new RegexValidator(regexs[i]);
			if (rv.isValid(str))
				return true;
		}
		return false;
	}

	public boolean checkRegex(String str, Pattern pattern) {
		if (!checkNull(str) || pattern == null)
			return false;
		return checkRegex_(str, pattern);
	}

	public boolean checkRegex(String str, Pattern[] patterns) {
		if (!checkNull(str) || patterns == null || patterns.length == 0)
			return false;
		return checkRegex_(str, patterns);
	}

	public boolean checkRegex(String str, String regex) {
		if (!checkNull(str))
			return false;
		if (!checkNull(regex))
			return false;
		return checkRegex_(str, regex);
	}

	public boolean checkRegex(String str, String[] regexs) {
		if (!checkNull(str) || regexs == null || regexs.length == 0)
			return false;
		return checkRegex_(str, regexs);
	}

	private boolean checkRegexI_(String str, String regex) {
		RegexValidator rv = new RegexValidator(regex, false);
		return rv.isValid(str);
	}

	private boolean checkRegexI_(String str, String[] regexs) {
		for (int i = 0; i < regexs.length; i++) {
			RegexValidator rv = new RegexValidator(regexs[i], false);
			if (rv.isValid(str))
				return true;
		}
		return false;
	}

	public boolean checkRegexI(String str, String regex) {
		if (!checkNull(str))
			return false;
		if (!checkNull(regex))
			return false;
		return checkRegexI_(str, regex);
	}

	public boolean checkRegexI(String str, String[] regexs) {
		if (!checkNull(str) || regexs == null || regexs.length == 0)
			return false;
		return checkRegexI_(str, regexs);
	}

	private void raiseRegExException(String str, String field) {
		if (commonUtils.requestUri().startsWith("/api/")) {
			if (checkNull(field))
				throw new ApiException(msgUtils.getMsg(Strs.ID_VALID_CHECK_REGEXMSG, field));
			else
				throw new ApiException(msgUtils.getMsg(Strs.ID_VALID_CHECK_REGEXMSG, str));
		} else {
			if (checkNull(field))
				throw new PageException(msgUtils.getMsg(Strs.ID_VALID_CHECK_REGEXMSG, field));
			else
				throw new PageException(msgUtils.getMsg(Strs.ID_VALID_CHECK_REGEXMSG, str));
		}
	}

	public void checkRegex(String str, String regex, String field) {
		if (checkRegex(str, regex))
			return;
		raiseRegExException(str, field);
	}

	public void checkRegexI(String str, String regex, String field) {
		if (checkRegexI(str, regex))
			return;
		raiseRegExException(str, field);
	}

	public void checkRegex(String str, String[] regexs, String field) {
		if (checkRegex(str, regexs))
			return;
		raiseRegExException(str, field);
	}

	public void checkRegexI(String str, String[] regexs, String field) {
		if (checkRegexI(str, regexs))
			return;
		raiseRegExException(str, field);
	}

}
