function v(p) {
  return axios.post("/api/auth/plugin/oldImport/oldVersionImport", p);
}
const n = window.Vue.createTextVNode, r = window.Vue.resolveComponent, t = window.Vue.withCtx, a = window.Vue.createVNode, b = window.Vue.unref, N = window.Vue.openBlock, P = window.Vue.createElementBlock, I = { class: "page" }, U = window.Vue.reactive, m = window.Vue.ref, w = window.ElementPlus.ElMessage, x = {
  __name: "OldImportView",
  setup(p) {
    let u = m(!1);
    const l = m({
      dataBaseIp: "",
      dataBasePort: "",
      dataBaseName: "",
      dataBaseUserName: "",
      dataBasePassword: ""
    }), c = U({
      dataBaseIp: [{ required: !0, message: "请输入老版本数据库IP", trigger: "blur" }],
      dataBasePort: [{ required: !0, message: "请输入老版本数据库端口", trigger: "blur" }],
      dataBaseName: [{ required: !0, message: "请输入老版本数据库名称", trigger: "blur" }],
      dataBaseUserName: [{ required: !0, message: "请输入老版本数据库账户", trigger: "blur" }],
      dataBasePassword: [{ required: !0, message: "请输入老版本数据库密码", trigger: "blur" }]
    }), i = m();
    function B() {
      i.value.validate((f) => {
        f && (u.value = !0, v(l.value).then((e) => {
          e.code === 200 ? w.success("导入完成") : w.success("导入失败"), u.value = !1;
        }));
      });
    }
    return (f, e) => {
      const V = r("el-alert"), d = r("el-form-item"), s = r("el-input"), _ = r("el-button"), g = r("el-form");
      return N(), P("div", I, [
        a(V, {
          type: "warning",
          closable: !1
        }, {
          default: t(() => e[5] || (e[5] = [
            n(" 注: 只会导入分类、标签、文章、友链、附件、用户、评论等数据库数据，如数据已存在则会覆盖更新!， 附件资源请手动将老版本根目录下resources/upload/attach目录中的内容复制到当前版本根目录resources/upload目录中, 以上步骤操作完毕后,如出现附件访问不到的问题,可在系统管理->附件存储策略中调整默认存储策略中的存储路径位置 ")
          ])),
          _: 1
        }),
        a(V, {
          type: "error",
          closable: !1
        }, {
          default: t(() => e[6] || (e[6] = [
            n(" 特别注意: 该插件建议在系统刚安装完毕后使用,不建议产生数据后再进行导入, 会造成新数据丢失!!!导入完成后请进行退出登录再登录操作!!! ")
          ])),
          _: 1
        }),
        a(g, {
          ref_key: "addFormRef",
          ref: i,
          model: l.value,
          "label-width": "120px",
          "status-icon": "",
          rules: c
        }, {
          default: t(() => [
            a(d),
            a(d, {
              label: "数据库IP",
              prop: "dataBaseIp"
            }, {
              default: t(() => [
                a(s, {
                  modelValue: l.value.dataBaseIp,
                  "onUpdate:modelValue": e[0] || (e[0] = (o) => l.value.dataBaseIp = o),
                  placeholder: "请输入老版本数据库IP"
                }, null, 8, ["modelValue"])
              ]),
              _: 1
            }),
            a(d, {
              label: "数据库端口",
              prop: "dataBasePort"
            }, {
              default: t(() => [
                a(s, {
                  modelValue: l.value.dataBasePort,
                  "onUpdate:modelValue": e[1] || (e[1] = (o) => l.value.dataBasePort = o),
                  placeholder: "请输入老版本数据库端口"
                }, null, 8, ["modelValue"])
              ]),
              _: 1
            }),
            a(d, {
              label: "数据库名称",
              prop: "dataBaseName"
            }, {
              default: t(() => [
                a(s, {
                  modelValue: l.value.dataBaseName,
                  "onUpdate:modelValue": e[2] || (e[2] = (o) => l.value.dataBaseName = o),
                  placeholder: "请输入老版本数据库名称"
                }, null, 8, ["modelValue"])
              ]),
              _: 1
            }),
            a(d, {
              label: "数据库账户",
              prop: "dataBaseUserName"
            }, {
              default: t(() => [
                a(s, {
                  modelValue: l.value.dataBaseUserName,
                  "onUpdate:modelValue": e[3] || (e[3] = (o) => l.value.dataBaseUserName = o),
                  placeholder: "请输入老版本数据库账户"
                }, null, 8, ["modelValue"])
              ]),
              _: 1
            }),
            a(d, {
              label: "数据库密码",
              prop: "dataBasePassword"
            }, {
              default: t(() => [
                a(s, {
                  modelValue: l.value.dataBasePassword,
                  "onUpdate:modelValue": e[4] || (e[4] = (o) => l.value.dataBasePassword = o),
                  placeholder: "请输入老版本数据库密码"
                }, null, 8, ["modelValue"])
              ]),
              _: 1
            }),
            a(d, null, {
              default: t(() => [
                a(_, {
                  type: "primary",
                  onClick: B,
                  loading: b(u)
                }, {
                  default: t(() => e[7] || (e[7] = [
                    n("开始导入")
                  ])),
                  _: 1
                }, 8, ["loading"])
              ]),
              _: 1
            })
          ]),
          _: 1
        }, 8, ["model", "rules"])
      ]);
    };
  }
};
export {
  x as default
};
