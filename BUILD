java_library(
    name = "simple-sharding",
    srcs = glob(["src/main/java/com/yuanwhy/simple/sharding/**/*.java"]),
    deps = [":druid"],
)

java_library(
  name="druid",
  visibility = ["//visibility:public"],
  exports = [
    "@com_alibaba_druid_1_0_28//jar",
  ],
)
