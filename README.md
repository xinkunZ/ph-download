## description

ph-download is a spring boot server that can
download pxxnhub video automatically by cron expression

## recommend usage

this system has no config file or something to change.
it just download all video in a given playlist

so, you can add all you like videos into a single playlist,
`java -jar -Dpd-download.playList=xxxx ph-download.jar `

## TODO

- [x] add docker support

```
docker run -d -p 8080:8080 -v ./config:/opt/docker/config -v ./download:/opt/docker/download zxk000000000/ph-downloader:latest
```

## thanks

* [PornHub-downloader-python](https://github.com/mariosemes/PornHub-downloader-python)

