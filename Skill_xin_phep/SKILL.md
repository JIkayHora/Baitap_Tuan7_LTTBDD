---
name: tao-don-nghi-phep
description: Tạo đơn xin nghỉ phép đúng quy định từ yêu cầu của người dùng, thông điệp chuẩn mực gửi giảng viên, thầy cô giáo về các vấn đề học tập:
   "xin nghỉ học", "xin phép vắng mặt", "xin nghỉ" và sau đó lưu thành tệp hoàn chỉnh.
---

# Quy trình tạo đơn xin nghỉ phép

1. **Thu thập thông tin:** Xác định họ tên, mã nhân viên, lý do nghỉ và thời gian nghỉ.
2. **Hỏi thông tin người dùng** Đưa ra yêu cầu nhập từng loại thông tin: Họ tên, mã nhân viên/ sinh viên, lí do nghỉ, thời gian nghỉ, tên giáo viên/ người cần gửi đến, mã phòng/môn học/ mã học phần.
3. **Đối chiếu quy định:** Đọc `references/quy_dinh_nghi_phep.md` để kiểm tra tính hợp lệ.
4. **Kiểm tra ngày phép:** Chạy `python scripts/kiem_tra_ngay_phep.py --id <mã_số>`.
5. **Tạo và lưu kết quả:**
   - Lấy bố cục từ `assets/mau_don_nghi_phep.md` và điền đầy đủ thông tin.
   - Lưu nội dung đơn hoàn chỉnh vào thư mục `D:\Output_skill/` theo định dạng tên tệp: `D:\Output_skill/don_nghi_<ma_nhan_vien>_<YYYY-MM-DD>.md`.
   - Trả về đường dẫn tệp đã lưu kèm bản xem trước cho người dùng.

## Khi nào kích hoạt Skill (Triggers)
Kích hoạt skill khi người dùng có ý định trao đổi với thầy cô, giảng viên, **ngay cả khi KHÔNG có chữ "email"** trong câu hỏi:
1. **Xin nghỉ học / vắng mặt:**
   - *"Em bị bệnh mai xin thầy cho em nghỉ học một buổi"*
   - *"Thầy ơi hôm nay nhà em có việc đột xuất không lên lớp được"*
   - *"I have a fever and need to miss tomorrow's lecture"*
2. **Xin gia hạn bài tập / deadline / nộp muộn:**
   - *"Xin cô cho nhóm em lùi deadline bài tập lớn sang tuần sau"*
   - *"Máy tính bị hỏng nên chưa kịp nộp lab, nhờ thầy cho em nộp muộn"*
   - *"Can I request a deadline extension for Assignment 2 from Dr. Smith?"*
3. **Hỏi bài / thắc mắc kiến thức / đồ án:**
   - *"Em chưa hiểu thuật toán A* thầy giảng hôm trước, nhờ thầy giải thích lại"*
   - *"Em gặp lỗi khi chạy code đồ án, muốn xin thầy lịch hẹn tư vấn"*
   - *"I have a question about slide 12 of the database lecture"*
## Các Mẫu Quy Chuẩn (Templates)

### Mẫu 1: Xin phép nghỉ học / vắng mặt (Leave of Absence)

```text
Tiêu đề: [Mã Môn Học - Tên Môn] - [Mã Lớp HP] - [MSSV] - [Họ và Tên] - Xin phép vắng mặt buổi học ngày [DD/MM/YYYY]

Kính gửi Thầy/Cô [Họ và tên Giảng viên],

Em tên là: [Họ và Tên Sinh Viên]
Mã số sinh viên: [MSSV]
Hiện đang theo học môn [Tên Môn Học], mã lớp [Mã Lớp Học Phần], ca học [Thứ / Tiết học].

Em viết thư này kính xin phép Thầy/Cô cho em được vắng mặt trong buổi học ngày [Thứ..., ngày DD/MM/YYYY] vì lý do: [Lý do cụ thể, ví dụ: bị sốt xuất huyết phải điều trị theo chỉ định của bác sĩ / gia đình có việc hiếu đột xuất].

Trong thời gian nghỉ, em cam kết sẽ:
1. Tự ôn tập kiến thức qua slide bài giảng và giáo trình.
2. Mượn vở ghi chép của các bạn trong lớp để bổ sung bài học.
3. Hoàn thành đầy đủ bài tập được giao cho buổi học này.

(Nếu có minh chứng: Em xin gửi đính kèm giấy chứng nhận y tế/giấy xác nhận của cơ sở y tế để Thầy/Cô xác thực ạ.)

Em kính mong Thầy/Cô thông cảm và tạo điều kiện cho em. Em xin chân thành cảm ơn Thầy/Cô!

Kính chúc Thầy/Cô luôn dồi dào sức khỏe và công tác tốt.

Kính thư,
[Họ và Tên Sinh Viên]
MSSV: [MSSV]
Lớp: [Mã Lớp Sinh Hoạt / Lớp HP]

### Mẫu 2: Xin nghỉ học do trùng lịch thi hoặc tham gia hoạt động của Trường/Khoa
Tiêu đề: [Mã Môn Học - Tên Môn] - [Mã Lớp HP] - [MSSV] - [Họ và Tên] - Xin phép vắng mặt ngày [DD/MM/YYYY] do [trùng lịch thi / tham gia hoạt động của Khoa]

Kính gửi Thầy/Cô [Họ và tên Giảng viên],

Em tên là: [Họ và Tên Sinh Viên]
Mã số sinh viên: [MSSV]
Hiện đang học lớp [Tên Môn Học] - Mã lớp [Mã Lớp Học Phần] (Ca học: [Thứ / Tiết học]).

Em viết email này kính xin phép Thầy/Cô cho em được vắng mặt trong buổi học ngày [Thứ..., ngày DD/MM/YYYY] vì lý do: [Chọn 1 lý do:
- Em có lịch thi kết thúc học phần môn [Tên Môn Thi] bị trùng khung giờ với buổi học của Thầy/Cô.
- Em được cử đại diện Khoa/Trường tham gia chương trình/cuộc thi [Tên Chương Trình/Cuộc Thi] diễn ra cùng thời điểm.]

Em xin gửi đính kèm bên dưới [ảnh chụp lịch thi từ cổng thông tin sinh viên / giấy xác nhận của Khoa hoặc Đoàn - Hội] để Thầy/Cô xem xét.

Về phần kiến thức của buổi học này, em xin cam kết sẽ tự nghiên cứu tài liệu, chép bài đầy đủ từ các bạn trong lớp và nộp bài tập đúng thời hạn quy định.

Kính mong Thầy/Cô xem xét và tạo điều kiện giúp đỡ em. Em xin trân trọng cảm ơn Thầy/Cô!

Kính thư,
[Họ và Tên Sinh Viên]
MSSV: [MSSV]
Lớp: [Mã Lớp Sinh Hoạt / Lớp HP]

### Mẫu 3: Xin nghỉ học vì việc gia đình đột xuất (Family Emergency)
Tiêu đề: [Mã Môn Học - Tên Môn] - [Mã Lớp HP] - [MSSV] - [Họ và Tên] - Xin phép nghỉ học buổi ngày [DD/MM/YYYY]

Kính gửi Thầy/Cô [Họ và tên Giảng viên],

Em tên là: [Họ và Tên Sinh Viên]
Mã số sinh viên: [MSSV]
Sinh viên lớp học phần [Tên Môn Học] ([Mã Lớp Học Phần]), thời gian học [Thứ / Tiết học].

Em làm đơn này kính mong Thầy/Cô cho phép em được nghỉ buổi học ngày [Thứ..., ngày DD/MM/YYYY] do gia đình em có việc đột xuất bất khả kháng ([ví dụ: gia đình có việc hiếu / người thân nhập viện cần em về quê gấp để hỗ trợ]).

Em xin cam kết sẽ chủ động liên hệ với bạn bè trong lớp để chép bài, cập nhật các thông báo mới từ Thầy/Cô và hoàn thành đầy đủ bài tập của buổi học vắng mặt (cũng như phối hợp với các thành viên trong nhóm nếu có bài tập nhóm).

Em rất mong nhận được sự thông cảm và chấp thuận của Thầy/Cô. Em xin chân thành cảm ơn Thầy/Cô!

Kính thư,
[Họ và Tên Sinh Viên]
MSSV: [MSSV]

### Mẫu 4: Xin nghỉ học vì lý do sức khỏe / khám chữa bệnh (Medical Leave)
Tiêu đề: [Mã Môn Học - Tên Môn] - [Mã Lớp HP] - [MSSV] - [Họ và Tên] - Xin phép vắng mặt buổi học ngày [DD/MM/YYYY]

Kính gửi Thầy/Cô [Họ và tên Giảng viên],

Em tên là: [Họ và Tên Sinh Viên]
Mã số sinh viên: [MSSV]
Hiện đang theo học môn [Tên Môn Học], mã lớp [Mã Lớp Học Phần], ca học [Thứ / Tiết học].

Em viết thư này kính xin phép Thầy/Cô cho em được vắng mặt trong buổi học ngày [Thứ..., ngày DD/MM/YYYY] vì lý do sức khỏe: [Mô tả ngắn gọn, ví dụ: em bị sốt cao và đau họng, cần nghỉ ngơi theo chỉ định của bác sĩ / em có lịch tái khám chuyên khoa tại bệnh viện].

Trong thời gian nghỉ, em cam kết sẽ:
1. Tự ôn tập nội dung bài học thông qua slide bài giảng và giáo trình.
2. Mượn vở ghi chép của các bạn trong lớp để bổ sung đầy đủ kiến thức.
3. Hoàn thành đúng hạn các bài tập và nhiệm vụ được giao trong buổi học này.

(Nếu có minh chứng: Em xin phép gửi đính kèm [giấy xác nhận khám bệnh / đơn thuốc] để Thầy/Cô tiện theo dõi và xác thực ạ.)

Em kính mong Thầy/Cô thông cảm và tạo điều kiện cho em. Em xin chân thành cảm ơn Thầy/Cô!

Kính chúc Thầy/Cô luôn dồi dào sức khỏe và công tác tốt.

Kính thư,
[Họ và Tên Sinh Viên]
MSSV: [MSSV]
Lớp: [Mã Lớp Sinh Hoạt / Lớp HP]