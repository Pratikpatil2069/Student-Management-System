import { Link } from "react-router-dom";
import { useEffect, useState } from "react";

function Home() {
  const [students, setStudents] = useState([]);
  useEffect(() => {
    fetch("http://localhost:8080/Student/getAll")
      .then((response) => response.json())
      .then((data) => {
        setStudents(data);
      });
  }, []);
  return (
    <div className="max-w-6xl mx-auto p-6">
      <h1 className="text-4xl font-bold text-center mb-10">
        Student Management System
      </h1>

      <div className="grid md:grid-cols-2 gap-6 mb-10">
        <Link to="/add-student">
          <div className="bg-blue-600 text-white p-8 rounded-xl shadow-md hover:scale-105 transition">
            <h2 className="text-2xl font-bold mb-2">Add Student</h2>

            <p>Add new student records to the system.</p>
          </div>
        </Link>

        <Link to="/students">
          <div className="bg-green-600 text-white p-8 rounded-xl shadow-md hover:scale-105 transition">
            <h2 className="text-2xl font-bold mb-2">View Students</h2>

            <p>View, update and delete student records.</p>
          </div>
        </Link>
      </div>

      <div className="bg-white rounded-xl shadow-md p-6">
        <h2 className="text-2xl font-semibold mb-4">Recent Students</h2>

        {students.slice(0, 5).map((student) => (
          <div key={student.id} className="border-b py-2">
            <p className="font-medium">{student.name}</p>

            <p className="text-gray-500 text-sm">{student.email}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Home;
