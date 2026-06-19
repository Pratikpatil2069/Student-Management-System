import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

function StudentList() {
  const [students, setStudents] = useState([]);

  const navigate = useNavigate();

  useEffect(() => {
    getAllStudents();
  }, []);

  function getAllStudents() {
    fetch("http://localhost:8080/Student/getAll")
      .then((response) => response.json())
      .then((data) => {
        setStudents(data);
      });
  }

  function deleteStudents(id) {
    const confirmDelete = window.confirm(
      "Are you sure you want to delete this student?",
    );

    if (!confirmDelete) {
      return;
    }

    fetch(`http://localhost:8080/Student/delete/${id}`, {
      method: "DELETE",
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        getAllStudents();
      });
  }

  function editStudents(id) {
    navigate(`/edit-student/${id}`);
  }

  return (
    <div className="max-w-6xl mx-auto p-6">
      <div className="bg-white p-6 rounded-xl shadow-md">
        <h2 className="text-3xl font-bold mb-6 text-center">Student List</h2>

        <div className="overflow-hidden rounded-lg border border-gray-200">
          <table className="w-full border-collapse">
            <thead>
              <tr className="bg-blue-600 text-white">
                <th className="border p-3 text-left">Name</th>
                <th className="border p-3 text-left">Age</th>
                <th className="border p-3 text-left">Email</th>
                <th className="border p-3 text-left">Actions</th>
              </tr>
            </thead>

            <tbody>
              {students.map((student) => (
                <tr
                  key={student.id}
                  className="hover:bg-gray-50 even:bg-gray-50"
                >
                  <td className="border p-3">{student.name}</td>

                  <td className="border p-3">{student.age}</td>

                  <td className="border p-3">{student.email}</td>

                  <td className="border p-3">
                    <button
                      onClick={() => editStudents(student.id)}
                      className="bg-green-500 text-white px-3 py-1 rounded-lg mr-2 hover:bg-green-600 transition"
                    >
                      Update
                    </button>

                    <button
                      onClick={() => deleteStudents(student.id)}
                      className="bg-red-500 text-white px-3 py-1 rounded-lg hover:bg-red-600 transition"
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

export default StudentList;
