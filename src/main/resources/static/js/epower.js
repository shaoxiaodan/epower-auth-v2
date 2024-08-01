
// 定义全局的默认角色id变量
var defroleidVal = 0;

/**
 * 初始化调用
 * 获取当前用户的defroleid
 */
function init(defroleid) {
	//alert("init...defroleid=" + defroleid);
	if (defroleid <= 0) {
		alert("角色不存在，请与管理员确认。");
		history.go(-1);
	}
	defroleidVal = defroleid;
}

/**
 * 切换用户默认角色
 */
function defRoleChange(defroleformid, defroleselectorid) {
	var defVal = 0;
	var defRoleFormObj = document.getElementById(defroleformid);
	var defRoleSelectorObj = document.getElementById(defroleselectorid);
	var defRoleOpts = defRoleSelectorObj.options;

	for (var i = 0; i < defRoleOpts.length; i++) {
		if (defRoleOpts[i].selected) {
			if (defRoleOpts[i].value == defroleidVal) {
				alert("当前已是角色：" + defRoleOpts[i].text);
				return false;
			} else {
				defVal = defRoleOpts[i].value;
			}
		}
	}

	//alert("defVal=" + defVal);
	defRoleFormObj.action = "/rolechange?def=" + defVal;
	return true;
}

/**
 * 检查下拉列表的值是否正确
 */
function checkOptValue(selectorid, msg) {
	var optVal = true;
	var selectorObj = document.getElementById(selectorid);
	var optionsObj = selectorObj.options;

	for (var i = 0; i < optionsObj.length; i++) {
		if (optionsObj[i].selected && optionsObj[i].value <= 0) {
			alert("请选择" + msg + "。");
			optVal = false;
			break;
		}
	}
	return optVal;
}

/**
 * 删除数据的确认提示框
 */
function delConfirm() {
	var result = confirm("是否确认删除当前数据？");
	//alert("选择结果：" + result);
	if (result) {
		return true;
	} else {
		return false;
	}
}
