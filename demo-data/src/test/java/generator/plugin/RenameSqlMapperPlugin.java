package generator.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * @Author: laizc
 * @Date: Created in  2020-08-13
 * @desc:
 */
public class RenameSqlMapperPlugin extends PluginAdapter {

	private String searchString;
	private String replaceString;
	private Pattern pattern;

	/**
	 *
	 */
	public RenameSqlMapperPlugin() {
	}

	public boolean validate(List<String> warnings) {

		searchString = properties.getProperty("searchString"); //$NON-NLS-1$
		replaceString = properties.getProperty("replaceString"); //$NON-NLS-1$

		boolean valid = stringHasValue(searchString)
				&& stringHasValue(replaceString);
		if (valid) {
			pattern = Pattern.compile(searchString);
		} else {
			if (!stringHasValue(searchString)) {
				warnings.add(getString("ValidationError.18", //$NON-NLS-1$
						"RenameSqlMapperPlugin", //$NON-NLS-1$
						"searchString")); //$NON-NLS-1$
			}
			if (!stringHasValue(replaceString)) {
				warnings.add(getString("ValidationError.18", //$NON-NLS-1$
						"RenameSqlMapperPlugin", //$NON-NLS-1$
						"replaceString")); //$NON-NLS-1$
			}
		}

		return valid;
	}

	@Override
	public void initialized(IntrospectedTable introspectedTable) {
		String oldType = introspectedTable.getMyBatis3XmlMapperFileName();
		Matcher matcher = pattern.matcher(oldType);
		oldType = matcher.replaceAll(replaceString);

		introspectedTable.setMyBatis3XmlMapperFileName(oldType);
	}
}
